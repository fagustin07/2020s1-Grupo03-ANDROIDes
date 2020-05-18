package org.unqflix.controllers

import domain.Content
import io.javalin.http.Context
import org.unqflix.mappers.ContentMapper
import org.unqflix.mappers.ContentSimpleMapper
import org.unqflix.model.UnqflixFactory

class AppController {
    private val backend = UnqflixFactory.takeSystem()

    fun getBanners(ctx : Context){
        ctx.json(ContentMapper(backend.banners).bannersView())
    }

    fun getContent(ctx : Context) {
        ctx.json(ContentMapper(content()).contentView())
    }

    fun getSpecifyContent(ctx: Context) {
        val content = contentSearched(ctx.queryParam("text")!!) // <-- habria que ver como levantar una excepcion si no hay texto ;)
        ctx.json(ContentMapper(content).contentView())

    }

    private fun contentSearched(text : String) : MutableCollection<Content>  {
        val content : MutableCollection<Content> = mutableListOf()
        backend.searchSeries(text).forEach {content.add(it)}
        backend.searchMovies(text).forEach {content.add(it)}
        return content
    }


    private fun content() : MutableCollection<Content> {
        val content : MutableCollection<Content> = mutableListOf()
        backend.series.forEach { content.add(it)}
        backend.movies.forEach { content.add((it))}
        return content
    }

}