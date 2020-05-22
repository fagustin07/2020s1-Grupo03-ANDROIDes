package org.unqflix

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin
import org.unqflix.token.JWTAccessManager
import org.unqflix.controllers.AppController
import org.unqflix.token.TokenJWT
import org.unqflix.controllers.UserController

fun main() { UNQFlixApi(7342).init() }

internal enum class Roles : Role {
    PUBLIC, USER
}

class UNQFlixApi(private val  port : Int) {
    private val token = TokenJWT()

    fun init() : Javalin {
        val app = Javalin.create {
            it.defaultContentType = "application/json"
            it.enableCorsForAllOrigins()
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.accessManager(JWTAccessManager(token))
        }.exception(Exception::class.java) { e, ctx ->
            e.printStackTrace()
            ctx.status(500)
            ctx.json("Error")
        }.start(port)

        val appController = AppController(token)
        val userController = UserController(token)

        app.routes {
            path("register") {
                post(userController::createUser, setOf(Roles.PUBLIC))
            }
            path("login") {
                post(userController::loginUser, setOf(Roles.PUBLIC) )
            }
            path("user") {
                get(userController::getUser, setOf(Roles.USER))
                path("fav") {
                    path(":contentId") {
                        get(appController::addOrRemoveContent, setOf(Roles.USER))
                    }
                }
                path("lastSeen") {
                    get(appController::addLastSeen, setOf(Roles.USER))
                }
            }
                path("banners") {
                    get(
                        appController::getBanners, setOf(Roles.USER))
                }
                path("content") {
                    get(appController::getContent, setOf(Roles.USER))
                    path(":contentId") {
                        get(appController::getContentById, setOf(Roles.USER))
                    }
                }
                path("search") {
                    get(appController::getSpecifyContent, setOf(Roles.USER))
                }

            }
        return app
    }
}
