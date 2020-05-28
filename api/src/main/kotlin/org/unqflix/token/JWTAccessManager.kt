package org.unqflix.token

import domain.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import org.unqflix.Roles
import org.unqflix.backend.UnqflixFactory

class JWTAccessManager(val tokenJWT : TokenJWT) : AccessManager {
    private val system= UnqflixFactory.takeSystem()

    private fun getUser(token: String): User {
        val userId = tokenJWT.validate(token)
        return system.users.firstOrNull { it.id == userId } ?: throw UnauthorizedResponse("User not found.")
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            token == null && roles.contains(Roles.PUBLIC) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found.")
            roles.contains(Roles.PUBLIC) -> throw UnauthorizedResponse("You already have a token.")
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
        }
    }
}