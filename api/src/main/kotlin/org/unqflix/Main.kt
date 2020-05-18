package org.unqflix

import domain.ExistsException
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
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
    }


    app.exception(ExistsException::class.java){ error, ctx ->
        ctx.status(400)
        ctx.json(mapOf("result" to error.message))
    }
}

