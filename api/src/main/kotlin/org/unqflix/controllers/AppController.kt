package org.unqflix.controllers

import domain.*
import io.javalin.http.Context
import org.unqflix.mappers.ContentMapper
import org.unqflix.mappers.MovieMapper
import org.unqflix.mappers.SerieMapper

class AppController : AbstractController(){

    fun getBanners(ctx : Context){
        ctx.json(generateContentView(system.banners))
    }

    fun getContent(ctx : Context) {
        val contentList = unify(system.series.toMutableList(),system.movies.toMutableList())

        ctx.json(contentList)
    }

    fun getSpecifyContent(ctx: Context) {
        var searchedText = ctx.queryParam("text")
        searchedText = searchedText ?: ""

        val contentList = unify(system.searchSeries(searchedText).toMutableList(),system.searchMovies(searchedText).toMutableList())

        ctx.json(contentList)
    }

    fun getContentById(ctx: Context){
        val contentId = ctx.pathParam("contentId")
        val content = findContentById(contentId)
        if(content::class==Serie::class) {
            val serieContent:Serie= content as Serie

            val serie= generateSerieMapper(serieContent)

            ctx.json(serie)
        }else{
            val movieContent:Movie= content as Movie

            val movie= generateMovieMapper(movieContent)

            ctx.json(movie)
        }
    }

    private fun takeCategoriesName(categories: MutableList<Category>)= categories.map { it.name }.toMutableList()

    private fun unify(contentToUnifyA: MutableList<Content>, contentToUnifyB: MutableList<Content>): MutableList<ContentMapper> {
        val contentList = mutableListOf<Content>()

        contentList.addAll(contentToUnifyA)
        contentList.addAll(contentToUnifyB)
        contentList.sortBy { it.title}

        return generateContentView(contentList)
    }

    private fun generateMovieMapper(movieContent: Movie): MovieMapper {
        return MovieMapper(
            movieContent.id, movieContent.title, movieContent.state::class == Available::class,
            movieContent.description, movieContent.poster, movieContent.video, movieContent.duration,
            movieContent.actors, movieContent.directors,
            takeCategoriesName(movieContent.categories), generateContentView(movieContent.relatedContent)
        )
    }

    private fun generateSerieMapper(serieContent: Serie): SerieMapper {
        return SerieMapper(
            serieContent.id, serieContent.title, serieContent.state::class == Available::class,
            serieContent.description, serieContent.poster, takeCategoriesName(serieContent.categories),
            serieContent.seasons, generateContentView(serieContent.relatedContent)
        )
    }
}