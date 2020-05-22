package org.unqflix.controllers

import domain.ExistsException
import domain.User
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.unqflix.mappers.*
import org.unqflix.model.IdGeneratorFactory
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.*
import org.unqflix.token.TokenJWT

class UserController(val tokenJWT: TokenJWT) {
    private val system=UnqflixFactory.takeSystem()

    fun createUser(ctx: Context){
        val newUserData = ctx.bodyValidator<NewUserMapper>()
            .check({ it.checkFields()},
                "Invalid body: name, email, password, credit card and image is required.")
            .get()
        ValidateUserData().validate(newUserData)

        val anNewUser= addToSystem(newUserData)

        ctx.status(201)
        ctx.json(ViewUserMapper(anNewUser.id,anNewUser.name,anNewUser.email,anNewUser.image))
    }

    fun loginUser(ctx: Context){
        val logInData= ctx.body<LogInDataMapper>()

        val user = system.users.find { it.email==logInData.email && it.password==logInData.password } ?:
        throw NotFoundResponse("Unable to authenticate. Invalid email or password.")

            ctx.header("Authorization", tokenJWT.generateToken(user))
            ctx.json(generateMessage("OK","Authenticated successfully!"))
        }

    fun getUser(ctx: Context){
        val idAuthenticated: String= tokenJWT.validate(ctx.header("Authorization")!!)

        val obtainedUser= system.users
            .find { it.id==idAuthenticated} ?: throw NotFoundResponse("User not found.")

        ctx.json(ViewUserDataMapper(obtainedUser.name,obtainedUser.image,
            generateContentView(obtainedUser.favorites), generateContentView(obtainedUser.lastSeen)))
    }

    private fun addToSystem(newUser: NewUserMapper): User {
        val theNewUser= generateUser(newUser)
        tryAddToSystem(theNewUser)

        return theNewUser
    }

    private fun tryAddToSystem(theNewUser: User) {
        try {
            system.addUser(theNewUser)
        } catch (error: ExistsException) {
            throw BadRequestResponse(error.message!!)
        }
    }

    private fun generateUser(newUser: NewUserMapper)= User(nextUserId(), newUser.name!!, newUser.creditCard!!,
                                                                    newUser.image!!,newUser.email!!,newUser.password!!)

    private fun nextUserId() = IdGeneratorFactory.takeIdGen().nextUserId()




}
