package org.unqflix.controllers

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import domain.User
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider


class UserGenerator() : JWTGenerator<User>{
    override fun generate(user: User, alg : Algorithm): String {
        val token = JWT.create().withClaim("id", user.id)
        return token.sign(alg);
    }
}

class TokenJWT
{
    val algorithm = Algorithm.HMAC256("very_secret")
    val generator = UserGenerator()
    val verifier = JWT.require(algorithm).build()
    val provider = JWTProvider(algorithm, generator, verifier)

    fun validate(token: String): String {
        val token = provider.validateToken(token)
        if (!token.isPresent) throw TokenNotPresent()
        return token.get().getClaim("id").asString()
    }

}

class TokenNotPresent : Exception("Token not present")
