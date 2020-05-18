package org.unqflix.controllers

import domain.User
import io.javalin.http.Context
import org.unqflix.mappers.*
import org.unqflix.model.IdGeneratorFactory
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.ValidateUserData

class UserController {
    private val system=UnqflixFactory.takeSystem()

    fun createUser(ctx: Context){
        val newUserData = ctx.bodyValidator<NewUserMapper>()
            .check({ it.checkFields()},
                "Invalid body: name, email, password, credit card and image is required.")
            .get()
        ValidateUserData().validate(newUserData)

        val anNewUser= tryAddToSystem(newUserData)

        ctx.status(201)
        ctx.json(ViewUserMapper(anNewUser).simpleView())
    }

    fun loginUser(ctx: Context){
        val logInData= ctx.body<LogInDataMapper>()

        if (system.users.any { logInData.match(it) }) {
            ctx.status(200)
            ctx.json(mapOf("result" to "Authenticated successfully!"))
        } else {
            ctx.status(404)
            ctx.json(mapOf("result" to "Error",
                "description" to "Unable to authenticate. Invalid email or password."))
        }
    }

    fun getUser(ctx: Context){
        val searchedUser=getUserByBackend(ctx.pathParam("id"))

        if(searchedUser!=null) {
            ctx.json(ViewUserMapper(searchedUser).complexView())
        }else{
            ctx.status(404)
            ctx.json(mapOf("result" to "Error",
            "description" to "User requested not found."))
        }
    }

    private fun tryAddToSystem(newUser: NewUserMapper): User {
        val theNewUser= generateUser(newUser)

        system.addUser(theNewUser)

        return theNewUser
    }

    private fun getUserByBackend(id : String)=system.users.find { it.id==id }

    private fun generateUser(newUser: NewUserMapper)= User(nextUserId(), newUser.name!!, newUser.creditCard!!,
                                                                    newUser.image!!,newUser.email!!,newUser.password!!)

    private fun nextUserId() = IdGeneratorFactory.takeIdGen().nextUserId()


}
