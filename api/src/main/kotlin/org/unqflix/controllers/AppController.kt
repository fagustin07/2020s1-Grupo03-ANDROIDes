package org.unqflix.controllers

import domain.*
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import org.unqflix.mappers.ContentMapper
import org.unqflix.mappers.MovieMapper
import org.unqflix.mappers.SerieMapper
import org.unqflix.model.UnqflixFactory
import org.unqflix.support.generateContentView
import org.unqflix.token.TokenJWT

class AppController(val tokenJWT:TokenJWT) {

    private val backend = UnqflixFactory.takeSystem()

    fun getBanners(ctx : Context){
        ctx.json(generateContentView(backend.banners))
    }

    fun getContent(ctx : Context) {
        val contentList = unify(backend.series.toMutableList(),backend.movies.toMutableList())

        ctx.json(contentList)
    }

    fun getSpecifyContent(ctx: Context) {
        var searchedText = ctx.queryParam("text")
        searchedText = searchedText ?: ""

        val contentList = unify(backend.searchSeries(searchedText).toMutableList(),backend.searchMovies(searchedText).toMutableList())

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

    fun addOrRemoveContent(ctx: Context){
        val obtainedUser= backend.users[0]

        val idContentSearched= ctx.pathParam("contentId")

        findContentById(idContentSearched)

        backend.addOrDeleteFav(obtainedUser.id, idContentSearched)

        ctx.status(200)
//        ctx.json(generateMessage("OK","Updated fav content"))
        ctx.json(obtainedUser.favorites)
    }

    fun addLastSeen (ctx: Context){
//        val obtainedUser= backend.users[0]
//        val logInData= ctx.body<idMapper>()
//        findContentById(logInData)
//
//        backend.addLastSeen(obtainedUser.id, logInData)
//
//        ctx.status(200)
//        ctx.json(generateMessage("OK","Content lastSeen added"))
//        ctx.json(obtainedUser.lastSeen)
    }

    private fun unify(contentToUnifyA: MutableList<Content>, contentToUnifyB: MutableList<Content>): MutableList<ContentMapper> {
        val contentList = mutableListOf<Content>()

        contentList.addAll(contentToUnifyA)
        contentList.addAll(contentToUnifyB)
        contentList.sortBy { it.title}

        return generateContentView(contentList)
    }

    private fun findContentById(id: String) : Content {
        var requestedContent:Content? = backend.series.firstOrNull { it.id == id }

        requestedContent =  requestedContent ?: backend.movies.firstOrNull { it.id == id }

        return requestedContent?: throw NotFoundResponse("Does not exist a content with id '$id'")
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