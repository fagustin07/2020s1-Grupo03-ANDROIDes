package org.unqflix

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import io.javalin.Javalin
import org.junit.After
import org.junit.Test
import org.junit.jupiter.api.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

class UNQFlixApiTest {

    private lateinit var api : Javalin

    @BeforeAll
    fun setup() {
        api = UNQFlixApi(7350).init()
        FuelManager.instance.basePath = "http://localhost:${api.port()}/"
    }

    @AfterAll
    fun tearDown(){
        api.stop()
    }
}
