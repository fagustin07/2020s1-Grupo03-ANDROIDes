package org.unqflix

import domain.ExistsException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.util.RouteOverviewConfig
import io.javalin.core.util.RouteOverviewPlugin
import io.javalin.http.BadRequestResponse
import org.unqflix.controllers.AppController
import org.unqflix.controllers.UserController
import org.unqflix.exception.InvalidFieldException
import org.unqflix.support.generateMessage

fun main() { UNQFlixApi(7342).init() }

//    val app = Javalin.create {
//        it.defaultContentType = "application/json"
//    }
//    app.start(7342)
//
//    val  appController = AppController()
//    val userController= UserController()
//
//    app.routes{
//        path("register"){
//            post(userController::createUser)
//        }
//        path("login"){
//            post(userController::loginUser)
//        }
//        path("user"){
//            get(userController::getUser)
//        }
//        path("banners"){
//            get(appController::getBanners)
//        }
//        path("content"){
//            get(appController::getContent)
//            path("/:contentId"){
//                get(appController::getContentById)
//            }
//        }
//        path("search"){
//            get(appController::getSpecifyContent)
//        }
//
//    }
//}

class UNQFlixApi(private val  port : Int)
{
    fun init(){
        val app = Javalin.create {
            it.defaultContentType = "application/json"
            it.enableCorsForAllOrigins()
            it.registerPlugin(RouteOverviewPlugin("/routes"))
        }.exception(Exception::class.java) { e,ctx ->
            e.printStackTrace()
            ctx.status(500)
            ctx.json("Error")
        }.start(7342)

        val  appController = AppController()
        val userController= UserController()

        app.routes{
            path("register"){
                post(userController::createUser)
            }
            path("login"){
                post(userController::loginUser)
            }
            path("user"){
                get(userController::getUser)
                path("fav"){
                    path(":contentId"){
                        get(appController::addOrRemoveContent)
                    }
                }
                path("lastSeen"){
                    get(appController::addLastSeen)
                }
            }
            path("banners"){
                get(appController::getBanners)
            }
            path("content"){
                get(appController::getContent)
                path(":contentId"){
                    get(appController::getContentById)
                }
            }
            path("search"){
                get(appController::getSpecifyContent)
            }

        }
    }
}
