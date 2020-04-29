package org.unqflix.view

import domain.*
import org.unqflix.model.UNQflixAppModel
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window


fun main(){
    ApplicationUNQflix().start()
}

class ApplicationUNQflix: Application() {

    override fun createMainWindow(): Window<*> {
        val idGenerator= IdGenerator()
        return UNQflixWindow(this, UNQflixAppModel(unqFlix(idGenerator), idGenerator))
    }

    private fun unqFlix(idGenerator: IdGenerator): UNQFlix{
        var baseCats = baseCategories(idGenerator)
        var baseSeas = baseSeasons(idGenerator)
        var dbz = Serie(
            title = "dragon ball z", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(),categories = mutableListOf(baseCats[1]),seasons = baseSeas)
        var oneP = Serie(
            title = "one piece", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(), categories = mutableListOf(baseCats[1]),seasons = baseSeas)
        var gint = Serie(
            title = "gintama", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Unavailable(),categories = mutableListOf(baseCats[1]),seasons = baseSeas)
        var dn = Serie(
            title = "death note", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Unavailable(),categories = mutableListOf(baseCats[1]),seasons = baseSeas)
        var snk = Serie(
            title = "attack on titan", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(),categories = mutableListOf(baseCats[1]),seasons = baseSeas)
        var opm = Serie(
            title = "one punch man", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(),categories = mutableListOf(baseCats[1]),seasons = baseSeas)

        var baseSeries= mutableListOf(dbz,oneP,gint,dn,snk,opm)

        return UNQFlix(series = baseSeries, categories = baseCats)

    }

    private fun baseCategories(idGenerator: IdGenerator): MutableList<Category> {
        var comedy = Category(idGenerator.nextCategoryId(),"Comedy")
        var anime = Category(idGenerator.nextCategoryId(),"Anime")
        var horror = Category(idGenerator.nextCategoryId(),"Horror")
        var csFic = Category(idGenerator.nextCategoryId(),"Cs. Fiction")
        var rltShow = Category(idGenerator.nextCategoryId(),"Reality Show")
        var trror = Category(idGenerator.nextCategoryId(),"Terror")
        var rmc = Category(idGenerator.nextCategoryId(),"Romance")
        return mutableListOf(comedy,anime,horror,csFic,rltShow,trror,rmc)
    }

    fun baseSeasons(idGenerator: IdGenerator) : MutableList<Season>{
        var baseCh = baseChapters(idGenerator)
        var season1 = Season(
            idGenerator.nextSeasonId(),"Season 1","La mejor temporada","google.com",chapters = baseCh)
        var season2 = Season(
            idGenerator.nextSeasonId(),"Season 2","La mejor temporada","google.com",chapters = baseCh)
        return mutableListOf(season1,season2)
    }

    fun baseChapters(idGenerator: IdGenerator) : MutableList<Chapter>{
        var chapter1 = Chapter(
            idGenerator.nextChapterId(),"Chapter 1","Pasan cosas",60,"","")
        var chapter2 = Chapter(
            idGenerator.nextChapterId(),"Chapter 2","Pasan mas cosas",90,"","")
        return mutableListOf(chapter1,chapter2)
    }
}
