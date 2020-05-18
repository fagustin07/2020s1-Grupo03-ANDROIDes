package org.unqflix.controllers

import io.javalin.http.Context
import org.unqflix.mappers.ContentViewMapper
import org.unqflix.model.UnqflixFactory

class AppController {
    val backend = UnqflixFactory.takeSystem()

    fun getBanners(ctx : Context){
        val banners = backend.banners.map { ContentViewMapper(it.id,it.title,it.description,it.state.toString()) }
        ctx.json(banners)
    }
}