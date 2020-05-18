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
        val content = search(ctx.queryParam("text")!!)
        ctx.json(ContentSimpleMapper(content.id,content.title,content.description,content.state))

    }

    private fun search(text : String)  = content().find { it.title == text } ?: throw Exception("")


    private fun content() : MutableCollection<Content> {
        val content : MutableCollection<Content> = mutableListOf()
        backend.series.forEach { content.add(it)}
        backend.movies.forEach { content.add((it))}
        return content
    }

}