package org.unqflix

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.unqflix.model.UnqflixFactory
import org.unqflix.token.TokenJWT
import kotlin.test.assertNotEquals

class TokenJWTTest
{

        val user = UnqflixFactory.takeSystem().users[0]

        @Test
        fun testGenerateToken() {
            val tokenJWT = TokenJWT()
            assertEquals(tokenJWT.generateToken(user), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9._KhWXeXAoIRAbYJDiCxbsnHSniq7FP8B8DcY8sRDow8")
        }

        @Test
        fun testGenerateTokenWithAnotherUser() {
            val tokenJWT = TokenJWT()
            assertNotEquals(tokenJWT.generateToken(user), "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfNiJ9.MiGsI-1qGABJA9IU1B4mUSoOH0XoKIg-PkyVP9Xuu14")
        }

        @Test
        fun testValidateToken() {
            val tokenJWT = TokenJWT()
            assertEquals(tokenJWT.validate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9._KhWXeXAoIRAbYJDiCxbsnHSniq7FP8B8DcY8sRDow8"), "u_1")
        }

//        @Test(expected =  NotFoundToken::class)
//        fun testValidateTokenWithInvalidToken() {
//            val tokenJWT = TokenJWT()
//            tokenJWT.validate("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfNDIifQ.wdzqNPF2zq-jTpUaWBEo1TlGD6Om5SwLQ3cfpV3wNYY")
//        }

    }
