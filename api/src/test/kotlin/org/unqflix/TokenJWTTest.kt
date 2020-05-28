package org.unqflix

import io.javalin.http.UnauthorizedResponse
import org.junit.Test
import org.unqflix.backend.UnqflixFactory
import org.unqflix.token.TokenJWT
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TokenJWTTest
{

    private val nicoUser = UnqflixFactory.takeSystem().users[0]

    @Test
    fun testGenerateToken() {
        val tokenJWT = TokenJWT()
        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74",
            tokenJWT.generateToken(nicoUser))
    }

    @Test
    fun testGenerateTokenWithAnotherUser() {
        val tokenJWT = TokenJWT()
        assertTrue("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfNiJ9.Zzvc4EsoDV4pW-Kq0cdpZmnS2hzG0yy5o54iHuoAfQk" !=
                tokenJWT.generateToken(nicoUser))
    }

    @Test
    fun testValidateToken() {
        val tokenJWT = TokenJWT()
        assertEquals("u_1",
            tokenJWT.validate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74"))
    }

        @Test(expected =  UnauthorizedResponse::class)
        fun testValidateTokenWithInvalidToken() {
            val tokenJWT = TokenJWT()
            tokenJWT.validate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJpZCI6InVfNiJ9.0ZysAKQpPtoDX2kJ5yhri7dTyWfZCF3Lq3TehRGgb_QlQ1j3UB0ZfAikhCmbrsCA")
        }

    }