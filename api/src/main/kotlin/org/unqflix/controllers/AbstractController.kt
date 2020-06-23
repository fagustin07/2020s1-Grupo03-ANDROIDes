package org.unqflix.controllers

import domain.Available
import domain.Content
import io.javalin.http.NotFoundResponse
import org.unqflix.mappers.ContentMapper
import org.unqflix.backend.UnqflixFactory

abstract class AbstractController {

    protected val system= UnqflixFactory.takeSystem()

    fun generateMessage(typeMessage: String, descriptionMessage: String)=
            mapOf("result" to typeMessage, "description" to descriptionMessage)

    fun generateContentView(contentList: MutableCollection<Content>)=
            contentList.map { ContentMapper(it.id,it.title,it.description,it.poster,it.state::class== Available::class) }.toMutableList()

    protected fun findContentById(id: String) : Content {
        var requestedContent: Content? = system.series.firstOrNull { it.id == id }

        requestedContent =  requestedContent ?: system.movies.firstOrNull { it.id == id }

        return requestedContent?: throw NotFoundResponse("Does not exist a content with id '$id'")
    }
}