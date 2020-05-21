package org.unqflix

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin
import org.unqflix.support.JWTAccessManager
import org.unqflix.controllers.AppController
import org.unqflix.token.TokenJWT
import org.unqflix.controllers.UserController
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.Roles

fun main() { UNQFlixApi(7342).init() }

class UNQFlixApi(private val  port : Int)
{
//    private val token = TokenJWT()

    //private val  system = UnqflixFactory.takeSystem()
    //private val  jwtAccessManager = JWTAccessManager(token, system)

    fun init() : Javalin {
        val app = Javalin.create {
            it.defaultContentType = "application/json"
            it.enableCorsForAllOrigins()
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            // it.accessManager(jwtAccessManager)
        }.exception(Exception::class.java) { e, ctx ->
            e.printStackTrace()
            ctx.status(500)
            ctx.json("Error")
        }.start(7342)


        val appController = AppController()
        val userController = UserController()

        app.routes {
            path("register") {
                post(userController::createUser)
            }
            path("login") {
                post(userController::loginUser)
            }
            path("user") {
                path(":id") {
                    get(userController::getUser)
                    path("fav") {
                        path(":contentId") {
                            get(appController::addOrRemoveContent)
                        }
                    }
//                path("lastSeen"){
//                    get(appController::addLastSeen)
//                }
                }
                path("banners") {
                    get(
                        appController::getBanners)
                }
                path("content") {
                    get(
                        appController::getContent)
                    path(":contentId") {
                        get(
                            appController::getContentById)
                    }
                }
                path("search") {
                    get(appController::getSpecifyContent)
                }

            }
        }
        return app
    }
}
