package org.unqflix.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.User
import javalinjwt.JWTGenerator
import javalinjwt.JWTProvider
import org.unqflix.exception.InvalidToken


class UserGenerator : JWTGenerator<User>{
    override fun generate(user: User, alg : Algorithm): String {
        val token = JWT.create().withClaim("id", user.id)
        return token.sign(alg);
    }
}

class TokenJWT
{
    private val algorithm = Algorithm.HMAC256("ANDROIDesTeam_very_secret_pass")
    private val generator = UserGenerator()
    private val verifier = JWT.require(algorithm).build()
    private val provider = JWTProvider(algorithm, generator, verifier)

    fun validate(token: String): String {
        val anTokenJWT = provider.validateToken(token)
        if (!anTokenJWT.isPresent) throw InvalidToken()

        return anTokenJWT.get().getClaim("id").asString()
    }

    fun generateToken(user: User): String = provider.generateToken(user)


}


