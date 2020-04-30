package org.unqflix.view

import domain.*
import org.unqflix.model.IdGeneratorFactory
import org.unqflix.model.UNQflixAppModel
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window


fun main(){
    ApplicationUNQflix().start()
}

class ApplicationUNQflix: Application() {

    override fun createMainWindow(): Window<*> {
        return UNQflixWindow(this, UNQflixAppModel(unqFlix()))
    }

    private fun unqFlix(): UNQFlix{
        val idGenerator = IdGeneratorFactory.takeIdGen()
        var baseCats = baseCategories()
        var baseSeas = baseSeasons()
        var dbz = Serie(
            title = "dragon ball z", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(),categories = mutableListOf(baseCats[1]),seasons = baseSeas)
        var oneP = Serie(
            title = "one piece", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(), categories = mutableListOf(baseCats[1]))
        var gint = Serie(
            title = "gintama", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Unavailable(),categories = mutableListOf(baseCats[1]))
        var dn = Serie(
            title = "death note", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Unavailable(),categories = mutableListOf(baseCats[1]))
        var snk = Serie(
            title = "attack on titan", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(),categories = mutableListOf(baseCats[1]))
        var opm = Serie(
            title = "one punch man", description = "dragon ball", id = idGenerator.nextSerieId(),
            poster = "google.com", state = Available(),categories = mutableListOf(baseCats[1]))

        var baseSeries= mutableListOf(dbz,oneP,gint,dn,snk,opm)

        return UNQFlix(series = baseSeries, categories = baseCats)

    }

    private fun baseCategories(): MutableList<Category> {
        val idGenerator = IdGeneratorFactory.takeIdGen()

        var comedy = Category(idGenerator.nextCategoryId(),"Comedy")
        var anime = Category(idGenerator.nextCategoryId(),"Anime")
        var horror = Category(idGenerator.nextCategoryId(),"Horror")
        var csFic = Category(idGenerator.nextCategoryId(),"Cs. Fiction")
        var rltShow = Category(idGenerator.nextCategoryId(),"Reality Show")
        var trror = Category(idGenerator.nextCategoryId(),"Terror")
        var rmc = Category(idGenerator.nextCategoryId(),"Romance")
        return mutableListOf(comedy,anime,horror,csFic,rltShow,trror,rmc)
    }

    fun baseSeasons() : MutableList<Season>{
        val idGenerator = IdGeneratorFactory.takeIdGen()

        var baseCh = baseChapters()
        var season1 = Season(
            idGenerator.nextSeasonId(),"saga de freezer","Freezer alto alien",
            "dragonballz.com/sagafreezer.jpg",chapters = baseCh)
        var season2 = Season(
            idGenerator.nextSeasonId(),"saga de cell","Cell es un monstruo verde re polenta",
            "dragonballz.com/sagadecell.jpg")
        return mutableListOf(season1,season2)
    }

    fun baseChapters() : MutableList<Chapter>{
        val idGenerator = IdGeneratorFactory.takeIdGen()

        var chapter1 = Chapter(
            idGenerator.nextChapterId(),"volamos hacia el espacio!","Termina la batalla entre saiyans" +
                    "y deciden ir a Namekusei.",60,"dragonballz.com/sagadefreezer1.mp4","")
        var chapter2 = Chapter(
            idGenerator.nextChapterId(),"el misterio de yunzabit!","La busqueda de la nave espacial de Dios.",
            90,"dragonballz.com/sagadefreezer2.mp4","")
        return mutableListOf(chapter1,chapter2)
    }
}
