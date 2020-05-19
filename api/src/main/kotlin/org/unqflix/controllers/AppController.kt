package org.unqflix.controllers

import domain.Content
import io.javalin.http.Context
import org.unqflix.mappers.ContentMapper
import org.unqflix.mappers.ContentSimpleMapper
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.generateContentView
import org.unqflix.support.generateMessage

class AppController {
    private val backend = UnqflixFactory.takeSystem()

    fun getBanners(ctx : Context){
        ctx.json(generateContentView(backend.banners))
    }

    fun getContent(ctx : Context) {
        ctx.json(obtainContent())
    }

    fun getSpecifyContent(ctx: Context) {
        val searchedText = ctx.queryParam("text")

        if(searchedText!= null && searchedText::class== String::class){
            ctx.json(contentSearched(searchedText))
        } else {
            ctx.status(400)
            ctx.json(generateMessage("Error","You have not send a text. Please, try again."))
        }
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