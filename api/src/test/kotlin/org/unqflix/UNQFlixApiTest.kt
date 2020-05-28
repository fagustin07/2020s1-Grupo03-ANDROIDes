package org.unqflix

import com.github.kittinunf.fuel.*
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.jackson.responseObject
import io.javalin.Javalin
import org.junit.jupiter.api.*
import org.unqflix.mappers.ContentMapper
import org.unqflix.mappers.MovieMapper
import org.unqflix.mappers.ViewUserMapper
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

    @Test @Order(1)
    fun `test 01, 200 confirmation code with a valid username and password`() {
        val (_,response, result)=Fuel.post("login")
            .jsonBody("{ \"email\" : \"nico@gmail.com\", \"password\" : \"Martinez2\"}").responseString()

        val expectedResponse = """{"result":"OK","description":"Authenticated successfully!"}"""

        assertEquals(200, response.statusCode)
        assertEquals(expectedResponse,result.get())
    }

    @Test @Order(2)
    fun `test 02, throw 404 error code with invalid username or password`() {
        val (_, response, _)=Fuel.post("login")
            .jsonBody("{ \"email\" : \"nicoas@gmail.com\", \"password\" : \"Marti\"}").responseString()

        assertEquals(404,response.statusCode)
    }

        @Test @Order(3)
    fun `test 03, Unqflix start with nine contents`(){
        Fuel.post("login")
            .jsonBody("{ \"email\" : \"nico@gmail.com\", \"password\" : \"Martinez2\"}")

        val (_, response, result) =
            Fuel.get("content")
                .header(Headers.AUTHORIZATION,
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
                .responseObject<List<ContentMapper>>()

        assertEquals(200, response.statusCode)
        assertEquals(9, result.get().size)
    }

    @Test @Order(4)
    fun `test 04, can obtain an user info with valid tokenJWT`(){
        val (_, response, result) =
            Fuel.get("user")
                .header(Headers.AUTHORIZATION,
                    "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
                .responseString()
        val expectedBody="""{"name":"Nico Martinez","image":"https://www.nico.com.ar/image.jpg","favorites":[{"id":"mov_1","title":"your name","description":"Una linda historia.","status":true},{"id":"ser_2","title":"gintama","description":"las aventuras de Gintoki Sakata","status":false},{"id":"ser_3","title":"death note","description":"el prota encuentra una libreta magica que mata gente","status":false}],"lastSeen":[{"id":"ser_6","title":"dragon ball z","description":"Goku quiere ser el mas fuerte del mundo","status":true},{"id":"mov_2","title":"john wick","description":"El gran John","status":true}]}"""

        assertEquals(200,response.statusCode)
        assertEquals(expectedBody,result.get())
    }

    @Test @Order(5)
    fun `test 05, GET user throw 401 error code if try to obtain user info with invalid tokenJWT or null token`(){
        val (_, response, _) = Fuel.get("user").responseString()

        assertEquals(401,response.statusCode)
    }

    @Test @Order(6)
    fun `test 06, POST register throw 400 error code if register with invalid name`(){
        val (_, response, _) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"a98f7\"}")
            .responseString()

        assertEquals(400,response.statusCode)
    }

    @Test @Order(7)
    fun `test 07, POST register throw 400 error code if register with invalid email`(){
        val (_, response, _) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"Federico Agustin\",\"email\" : \"jaja @gmail..c\"}")
            .responseString()

        assertEquals(400,response.statusCode)
    }

    @Test @Order(8)
    fun `test 08, POST register throw 400 error code if register with invalid password`(){
        val (_, response, _) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"Federico Agustin\",\"email\" : \"federicos@gmail.com\"," +
                    "\"password\" : \"9w8a0980a\"}")
            .responseString()

        assertEquals(400,response.statusCode)
    }

    @Test @Order(9)
    fun `test 09, POST register throw 400 error code if register with invalid credit card number`(){
        val (_, response, _) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"Federico Agustin\",\"email\" : \"federicos@gmail.com\"," +
                    "\"password\" : \"9w8a0980aF\",\"creditCard\" : \"0078954642\"}")
            .responseString()

        assertEquals(400,response.statusCode)
    }

    @Test @Order(10)
    fun `test 10, POST register throw 400 error code if register with invalid image url`(){
        val (_, response, _) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"Federico Agustin\",\"email\" : \"federicos@gmail.com\"," +
                    "\"password\" : \"9w8a0980aF\",\"creditCard\" : \"7895 4879 5978 2356\"," +
                    "\"image\" : \"google.com/src\"}")
            .responseString()

        assertEquals(400,response.statusCode)
    }

    @Test @Order(11)
    fun `test 11, POST register throw 401 error code if register with a token`(){
        val (_, response, _) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"Federico Agustin\",\"email\" : \"federicos@gmail.com\"," +
                    "\"password\" : \"9w8a0980aF\",\"creditCard\" : \"7895 4879 5978 2356\"," +
                    "\"image\" : \"google.com/src\"}")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseString()

        assertEquals(401,response.statusCode)
    }

    @Test @Order(12)
    fun `test 12, POST register store a new user`(){
        val (_, response, result) = Fuel.post("register")
            .jsonBody("{ \"name\" : \"Federico Agustin\",\"email\" : \"federicos@gmail.com\"," +
                    "\"password\" : \"9w8a0980aF\",\"creditCard\" : \"7895 4879 5978 2356\"," +
                    "\"image\" : \"google.com/image34.jpg\"}")
            .responseObject<ViewUserMapper>()

        assertEquals(201,response.statusCode)
        assertEquals(
            ViewUserMapper("u_4","Federico Agustin","federicos@gmail.com", "google.com/image34.jpg"),
            result.get())
    }

    @Test @Order(13)
    fun `test 13, GET banners returns system banner if have a valid token`(){
        val (_, response, result) = Fuel.get("banners")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(200,response.statusCode)
        assertEquals(4,result.get().size)
    }

    @Test @Order(14)
    fun `test 14, GET banners throw 401 error code if have a invalid token`(){
        val (_, response, _) = Fuel.get("banners")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC5nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(401,response.statusCode)
    }


    @Test @Order(15)
    fun `test 15, GET search returns system titles contents who match with the query param if have a valid token`(){
        val (_, response, result) = Fuel.get("search",listOf("text" to "an"))
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(200,response.statusCode)
        assertEquals(3,result.get().size)
    }

    @Test @Order(16)
    fun `test 16, GET search throw 401 error code if have not a valid token`(){
        val (_, response, _) = Fuel.get("search",listOf("text" to "an"))
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK98")
            .responseObject<List<ContentMapper>>()

        assertEquals(401,response.statusCode)
    }

    @Test @Order(17)
    fun `test 17, GET content give the content requested by id if that exist and have a valid token`(){
        val (_, response, result) = Fuel.get("content/mov_2")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<MovieMapper>()

        val expectedResponse=MovieMapper("mov_2","john wick",true,"El gran John",
            "johnwick.com/poster.png","johnwick.com/movie.mp4",107, listOf(), listOf(),
            listOf("Cs. Fiction"), listOf())

        assertEquals(200,response.statusCode)
        assertEquals(expectedResponse, result.get())
    }

    @Test @Order(18)
    fun `test 18, GET content throw 404 error code if the id content requested id don't exist and you have a valid token`(){
        val (_, response, _) = Fuel.get("content/mov_98")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<MovieMapper>()

        assertEquals(404,response.statusCode)
    }

    @Test @Order(19)
    fun `test 19, GET content throw 401 error code if you have not a valid token`(){
        val (_, response, _) = Fuel.get("content/mov_1")
            .responseObject<MovieMapper>()

        assertEquals(401,response.statusCode)
    }

    @Test @Order(20)
    fun `test 20, POST user-fav throw 401 error code if you have not a valid token`(){
        val (_, response, _) = Fuel.post("user/fav/mov_1")
            .responseObject<MovieMapper>()

        assertEquals(401,response.statusCode)
    }

    @Test @Order(21)
    fun `test 21, POST user-fav add an content to fav user list if the content is not in the list with a valid token`(){
        val (_, response, result) = Fuel.post("user/fav/mov_3")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(200,response.statusCode)
        assertEquals(4, result.get().size)
    }

    @Test @Order(22)
    fun `test 22, POST user-fav delete an content to fav user list if the content is in the list with a valid token`(){
        val (_, response, result) = Fuel.post("user/fav/mov_3")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(200,response.statusCode)
        assertEquals(3, result.get().size)
    }

    @Test @Order(23)
    fun `test 23, POST user-fav throw 404 error code if the content id don't exist in the system and have a valid token`(){
        val (_, response, result) = Fuel.post("user/fav/mov_2")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(200,response.statusCode)
        assertEquals(4, result.get().size)
    }

    @Test @Order(24)
    fun `test 24, POST user-lastSeen throw 404 error code if the content id don't exist in the system and have a valid token`() {
        val (_, response, _) = Fuel.post("user/lastSeen")
            .jsonBody("{ \"id\" : \"mov_976\"}")
            .header(
                Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74"
            )
            .responseObject<List<ContentMapper>>()

        assertEquals(404, response.statusCode)
    }

    @Test @Order(25)
    fun `test 25, POST user-lastSeen add an content to last seen user list if have a valid token`(){
        val (_, response, result) = Fuel.post("user/lastSeen")
            .jsonBody("{ \"id\" : \"mov_3\"}")
            .header(Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK74")
            .responseObject<List<ContentMapper>>()

        assertEquals(200,response.statusCode)
        assertEquals(3, result.get().size)
    }

    @Test @Order(26)
    fun `test 26, POST user-lastSeen throw 401 error code if have not a valid token`() {
        val (_, response, _) = Fuel.post("user/lastSeen")
            .jsonBody("{ \"id\" : \"mov_976\"}")
            .header(
                Headers.AUTHORIZATION,
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6InVfMSJ9.cWx28Ew-4_kpcozPLfalSFXPZGyg6HcQl2mC8nxxK00"
            )
            .responseObject<List<ContentMapper>>()

        assertEquals(401, response.statusCode)
    }

}