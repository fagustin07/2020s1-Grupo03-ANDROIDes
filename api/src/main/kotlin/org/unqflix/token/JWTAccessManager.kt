package org.unqflix.token

import domain.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import org.unqflix.Roles
import org.unqflix.exception.InvalidToken
import org.unqflix.exception.NotFoundUser
import org.unqflix.model.UnqflixFactory

class JWTAccessManager(val tokenJWT : TokenJWT) : AccessManager {
    private val system= UnqflixFactory.takeSystem()

    private fun getUser(token: String): User {
        try {
            val userId = tokenJWT.validate(token)
            return system.users.firstOrNull { it.id == userId } ?: throw NotFoundUser()
        } catch (e: InvalidToken) {
            throw UnauthorizedResponse("Invalid Token.")
        } catch (e: NotFoundUser) {
            throw UnauthorizedResponse("Not found User.")
        }
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            token == null && roles.contains(Roles.PUBLIC) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.PUBLIC) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
        }
    }
}