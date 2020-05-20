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
        val result = obtainContent()
        result.sortBy { it.title.toLowerCase() }
        ctx.json(result)
    }

    fun getSpecifyContent(ctx: Context) {
        val searchedText = ctx.queryParam("title").toString().toLowerCase()

        ctx.json(contentSearched(searchedText))
    }

    fun getContentById(ctx: Context){
        val contentId = ctx.pathParam("contentId")
        val content = getMovieOrSerie(contentId)
            ?: throw NotFoundResponse("No existe el contenido con id '$contentId'")
        ctx.json(content)
    }

    private fun getMovieOrSerie(id: String) : Content? {
        val result = backend.series.firstOrNull { it.id == id }
        if (result == null) {
            return backend.movies.firstOrNull { it.id == id }
        } else { return result }
    }

    private fun obtainContent(): MutableList<ContentSimpleMapper> {
        val content = mutableListOf<Content>()
        addAllTo(content,backend.series.toMutableList())
        addAllTo(content,backend.movies.toMutableList())

        return generateContentView(content)
    }

    private fun contentSearched(text : String) : MutableList<ContentSimpleMapper>  {
        val content = mutableListOf<Content>()
        addAllTo(content, backend.searchSeries(text).toMutableList())
        addAllTo(content, backend.searchMovies(text).toMutableList())

        return generateContentView(content)
    }

    private fun addAllTo(content: MutableList<Content>, contentToAdd: MutableList<Content>)= content.addAll(contentToAdd)
}