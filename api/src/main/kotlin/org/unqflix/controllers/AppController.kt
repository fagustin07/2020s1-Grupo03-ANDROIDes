package org.unqflix.controllers

import domain.Available
import io.javalin.http.Context
import org.unqflix.mappers.ContentMapper
import org.unqflix.model.UnqflixFactory

class AppController {
    private val backend = UnqflixFactory.takeSystem()

    fun getBanners(ctx : Context){
        ctx.json(backend.banners.map{ ContentMapper(it.id,it.title, it.description,it.state)}.toList())
    }
}