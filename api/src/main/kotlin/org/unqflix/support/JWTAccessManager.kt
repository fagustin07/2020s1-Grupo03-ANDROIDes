package org.unqflix.support

import domain.UNQFlix
import domain.User
import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import org.unqflix.exception.NotFoundToken
import org.unqflix.exception.NotFoundUser
import org.unqflix.token.TokenJWT

class JWTAccessManager(val tokenJWT : TokenJWT, val system : UNQFlix) : AccessManager {

    fun getUser(token: String): User {
        try {
            val userId = tokenJWT.validate(token)
            return system.users.firstOrNull { it.id == userId } ?: throw NotFoundUser()
        } catch (e: NotFoundToken) {
            throw UnauthorizedResponse("Token not found")
        } catch (e: NotFoundUser) {
            throw UnauthorizedResponse("Invalid Token")
        }
    }

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            token == null && roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            token == null -> throw UnauthorizedResponse("Token not found")
            roles.contains(Roles.ANYONE) -> handler.handle(ctx)
            roles.contains(Roles.USER) -> {
                getUser(token)
                handler.handle(ctx)
            }
            roles.contains(Roles.ADMIN) -> {
                handler.handle(ctx)


            }
        }
    }
}