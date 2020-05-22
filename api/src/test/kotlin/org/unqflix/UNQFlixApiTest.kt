package org.unqflix

import io.javalin.Javalin
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)

class UNQFlixApiTest {

    private lateinit var api : Javalin

    @BeforeAll
    fun setup() {
        api = UNQFlixApi(7350).init()
        // FuelManager.instance.basePath = "http://localhost:${api.port()}/"
    }

    @AfterAll
    fun tearDown(){
        api.stop()
    }


//    @Test @Order(1)
//    fun `test 1`(){
//        val (_, response, result) = Fuel.get("http://localhost:${api.port()}/content").responseObject<List<ContentSimpleMapper>>()
//        assertEquals(200, response.statusCode)
//        assertEquals(8, result.get().size)
//        assertFalse(result.get().isEmpty())
//    }
//    fun `test 1`(){
//        val userJson = """
//            {
//                "name": "Carlos ",
//                "password": "12345",
//                "creditCard": "0000 0000 0000 0000",
//                "image": "http://image.com.ar",
//                "email": "carlitos234@gmail.com"
//            }
//        """
//        val (_, response, _) = Fuel.post("register").jsonBody(userJson).responseObject<String>()
//        assertEquals(201, response.statusCode)
//        assertTrue(response.body().isEmpty())
//    }


}
