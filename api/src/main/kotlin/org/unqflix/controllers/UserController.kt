package org.unqflix.controllers

import domain.NotFoundException
import domain.User
import io.javalin.http.Context
import org.unqflix.exceptions.UserDontExistException
import org.unqflix.mappers.*
import org.unqflix.model.IdGeneratorFactory
import org.unqflix.model.UnqflixFactory

class UserController {
    private val system=UnqflixFactory.takeSystem()

    fun createUser(ctx: Context){
        val newUserData = ctx.bodyValidator<NewUserMapper>()
            .check({ it.checkFields()},
                "Invalid body: name, email, password, credit card and image is required.")
            .get()
        val anNewUser= tryAddToSystem(newUserData)

        ctx.status(201)
        ctx.json(ViewUserMapper(anNewUser.id, anNewUser.name, anNewUser.email))
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
        val user = getUserByBackend(ctx.pathParam("id"))
        ctx.json(UserViewMapper(user.name,user.image))
        ctx.json(favorites(user))
        ctx.json(lastSeen(user))

    }

    private fun tryAddToSystem(newUser: NewUserMapper): User {
        val theNewUser= generateUser(newUser)

        system.addUser(theNewUser)

        return theNewUser
    }
    private fun getUserByBackend(id : String) : User {
        val user = system.users.firstOrNull { it.id == id }
            ?: throw UserDontExistException("User do not exist")
        return user
    }


    private fun favorites(user: User) : List<ContentViewMapper>{
        return  user.favorites.map { ContentViewMapper(it.id,it.title,it.description,it.state.toString()) }
    }

    private fun lastSeen(user: User) : List<ContentViewMapper>{
        return user.lastSeen.map { ContentViewMapper(it.id,it.title,it.description,it.state.toString()) }
    }


    private fun generateUser(newUser: NewUserMapper)= User(nextUserId(), newUser.name!!, newUser.creditCard!!,
                                                                    newUser.image!!,newUser.email!!,newUser.password!!)

    private fun nextUserId() = IdGeneratorFactory.takeIdGen().nextUserId()


}
