package org.unqflix.controllers

import domain.Content
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.unqflix.mappers.ContentSimpleMapper
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.generateContentView

class AppController {
    private val backend = UnqflixFactory.takeSystem()

    fun getBanners(ctx : Context){
        ctx.json(generateContentView(backend.banners))
    }

    fun getContent(ctx : Context) {
        val contentList = unify(backend.series.toMutableList(),backend.movies.toMutableList())

        ctx.json(contentList)
    }

    fun getSpecifyContent(ctx: Context) {
        val searchedText = ctx.queryParam("text")!!.toLowerCase()
        val contentList = unify(backend.searchSeries(searchedText).toMutableList(),backend.searchMovies(searchedText).toMutableList())

        ctx.json(contentList)
    }

    fun getContentById(ctx: Context){
        val contentId = ctx.pathParam("contentId")
        val content = getContent(contentId)
            ?: throw NotFoundResponse("Does not exist a content with id '$contentId'")
        ctx.json(content)
    }

    fun addOrRemoveContent(ctx: Context){
        val obtainedUser= backend.users[0]

        val contentToAddOrRemove= ctx.pathParam("contentId")

        if (obtainedUser.favorites.any { contentToAddOrRemove.match(it) }){
            // eliminar contenido
            // obtainedUser.favorites.remove(contentToAddOrRemove)
            // obtainedUser.removeFav(contentToAddOrRemove)
            ctx.status(200)
            ctx.json(generateMessage("OK","Removed content"))
        } else {
            // agregar contenido
            // obtainedUser.favorites.add(contentToAddOrRemove)
            // obtainedUser.addFav(contentToAddOrRemove)
            ctx.status(200) //los dos tienen el mismo status, hay que sacarlos afuera del if
            ctx.json(generateMessage("OK","Content added")) //idem pero mensaje diferente
        }

        ctx.json(obtainedUser.favorites)// no sé porque, pero no puedo iniciar la api
    }

    fun addLastSeen (ctx: Context){
        val obtainedUser= system.users[0]
        val logInData= ctx.body<LogInDataMapper>()
                                        // negarlo para no poner un else
        if (obtainedUser.lastSeen.any { logInData.match(it) }){ //tendría que agarrar solo el nombre?
            // no haría nada? lo saco y lo vuelvo a agregar asi esta al final?
        } else {
            // agregar contenido
            // obtainedUser.lastSeen.add(logInData)
            // obtainedUser.addLastSeen(logInData)
            ctx.status(200)
            ctx.json(generateMessage("OK","Content lastSeen added"))
        }

        ctx.json(obtainedUser.lastSeen)
    }

    private fun unify(contentToUnifyA: MutableList<Content>, contentToUnifyB: MutableList<Content>): MutableList<ContentSimpleMapper> {
        val contentList = mutableListOf<Content>()

        contentList.addAll(contentToUnifyA)
        contentList.addAll(contentToUnifyB)
        contentList.sortedBy { it.title}

        return generateContentView(contentList)
    }

    private fun getContent(id: String) : Content? {
        var requestedContent:Content? = backend.series.firstOrNull { it.id == id }
        if (requestedContent == null) requestedContent = backend.movies.firstOrNull { it.id == id }
        return requestedContent
    }

    private fun addAllTo(content: MutableList<Content>, contentToAdd: MutableList<Content>)= content.addAll(contentToAdd)
}