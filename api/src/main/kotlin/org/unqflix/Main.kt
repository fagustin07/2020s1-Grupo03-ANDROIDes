package org.unqflix

import domain.ExistsException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.unqflix.controllers.AppController
import org.unqflix.controllers.UserController

fun main() {

    val app = Javalin.create {
        it.defaultContentType = "application/json"
    }
    app.start(7342)

    val userController= UserController()
    
    app.routes{
        path("users"){
            post(userController::createUser)
        }
        path("login"){
            post(userController::loginUser)
        }
        path(":id"){
            get(userController::getUser)
        }
    }

    val  appController = AppController()

    app.routes{
        path("banners"){
            get(appController::getBanners)
        }
    }

    app.exception(ExistsException::class.java){ error, ctx ->
        ctx.status(400)
        ctx.json(mapOf("result" to error.message))
    }
}

