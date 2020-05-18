package org.unqflix

import domain.ExistsException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.unqflix.controllers.AppController
import org.unqflix.controllers.UserController
import org.unqflix.exception.InvalidFieldException

fun main() {

    val app = Javalin.create {
        it.defaultContentType = "application/json"
    }
    app.start(7342)

    val  appController = AppController()
    val userController= UserController()

    app.routes{
        path("users"){
            post(userController::createUser)
            path(":id"){
                get(userController::getUser)
            }
        }
        path("login"){
            post(userController::loginUser)
        }

        path("banners"){
            get(appController::getBanners)
        }
    }


    app.exception(ExistsException::class.java){ error, ctx ->
        ctx.status(400)
        ctx.json(mapOf("result" to "Error",
            "description" to error.message))
    }

    app.exception(InvalidFieldException::class.java){ error, ctx->
        ctx.status(400)
        ctx.json(mapOf("result" to "Error",
            "description" to error.message))
    }
}

