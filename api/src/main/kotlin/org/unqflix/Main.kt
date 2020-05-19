package org.unqflix

import domain.ExistsException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import org.unqflix.controllers.AppController
import org.unqflix.controllers.UserController
import org.unqflix.exception.InvalidFieldException
import org.unqflix.support.generateMessage

fun main() {

    val app = Javalin.create {
        it.defaultContentType = "application/json"
    }
    app.start(7342)

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
        }
        path("banners"){
            get(appController::getBanners)
        }
        path("content"){
            get(appController::getContent)
        }
        path("search"){
            get(appController::getSpecifyContent)
        }
    }


    app.exception(ExistsException::class.java){ error, ctx ->
        ctx.status(400)
        ctx.json(generateMessage("Error", error.message!!))
    }

    app.exception(InvalidFieldException::class.java){ error, ctx->
        ctx.status(400)
        ctx.json(generateMessage("Error", error.message!!))
    }
}

