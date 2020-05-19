package org.unqflix.controllers

import domain.User
import io.javalin.http.Context
import org.unqflix.mappers.*
import org.unqflix.model.IdGeneratorFactory
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.*

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
        ctx.json(ViewUserMapper(anNewUser.id,anNewUser.name,anNewUser.email,anNewUser.image))
    }

    fun loginUser(ctx: Context){
        val logInData= ctx.body<LogInDataMapper>()

        if (system.users.any { logInData.match(it) }) {
            ctx.status(200)
            ctx.json(generateMessage("OK","Authenticated successfully!"))
        } else {
            ctx.status(404)
            ctx.json(generateMessage("Error", "Unable to authenticate. Invalid email or password."))
        }
    }

    fun getUser(ctx: Context){
        val obtainedUser= system.users[0]
        ctx.json(ViewUserDataMapper(obtainedUser.name,obtainedUser.image,
            generateContentView(obtainedUser.favorites), generateContentView(obtainedUser.lastSeen)))
    }

    private fun tryAddToSystem(newUser: NewUserMapper): User {
        val theNewUser= generateUser(newUser)
        system.addUser(theNewUser)

        return theNewUser
    }

    private fun generateUser(newUser: NewUserMapper)= User(nextUserId(), newUser.name!!, newUser.creditCard!!,
                                                                    newUser.image!!,newUser.email!!,newUser.password!!)

    private fun nextUserId() = IdGeneratorFactory.takeIdGen().nextUserId()




}
