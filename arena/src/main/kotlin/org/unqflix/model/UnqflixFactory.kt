package org.unqflix.model

import domain.*

object UnqflixFactory {

    private var unqflixModel = UNQFlix()
    val idGenerator = IdGeneratorFactory.takeIdGen()



    init {
        val idGenerator = IdGeneratorFactory.takeIdGen()
        var baseCats = baseCategories()

        var dbz = Serie(
            title = "dragon ball z", description = "Goku quiere ser el mas fuerte del mundo", id = idGenerator.nextSerieId(),
            poster = "dragonballz.net/poster.jpg", state = Available(), categories = mutableListOf(baseCats[1],baseCats[4]),
            seasons =  baseSeasons()
        )
        var oneP = Serie(
            title = "one piece", description = "las aventuras de los Mugiwara!", id = idGenerator.nextSerieId(),
            poster = "onepiece.net/poster.jpg", state = Available(), categories = mutableListOf(baseCats[1],baseCats[3])
        )
        var gint = Serie(
            title = "gintama", description = "las aventuras de Gintoki Sakata", id = idGenerator.nextSerieId(),
            poster = "gintama.org/poster.jpg", state = Unavailable(), categories = mutableListOf(baseCats[0],baseCats[1])
        )
        var dn = Serie(
            title = "death note", description = "el prota encuentra una libreta magica que mata gente",
            id = idGenerator.nextSerieId(), poster = "deadnote.com/poster.jpg", state = Unavailable(),
            categories = mutableListOf(baseCats[1])
        )
        var snk = Serie(
            title = "attack on titans", description = "Un mundo en donde los titanes cazan personas.",
            id = idGenerator.nextSerieId(), poster = "attackontitans.com/poster.jpg", state = Available(),
            categories = mutableListOf(baseCats[1])
        )
        var opm = Serie(
            title = "one punch man", description = "Saitama es el hombre mas fuerte del mundo", id = idGenerator.nextSerieId(),
            poster = "onepunchman.com/poster.jpg", state = Available(), categories = mutableListOf(baseCats[1])
        )

        var baseSeries = mutableListOf(dbz, oneP, gint, dn, snk, opm)

        unqflixModel= UNQFlix(series = baseSeries, categories = baseCats)

    }


    private fun baseCategories(): MutableList<Category> {
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
        var baseCh = baseChapters()
        var season1 = Season(
            idGenerator.nextSeasonId(),"saga de freezer","Freezer es re maligno",
            "dragonballz.com/sagafreezer.jpg",chapters = baseCh)
        var season2 = Season(
            idGenerator.nextSeasonId(),"saga de cell","Cell es un monstruo verde re polenta",
            "dragonballz.com/sagadecell.jpg")
        return mutableListOf(season1,season2)
    }

    fun baseChapters() : MutableList<Chapter>{
        val idGenerator = IdGeneratorFactory.takeIdGen()

        var chapter1 = Chapter(
            idGenerator.nextChapterId(),"Volamos hacia el espacio!","Termina la batalla entre saiyans" +
                    "y deciden ir a Namekusei.",60,"dragonballz.com/sagadefreezer1.mp4","")
        var chapter2 = Chapter(
            idGenerator.nextChapterId(),"El misterio de yunzabit!","La busqueda de la nave espacial de Dios.",
            90,"dragonballz.com/sagadefreezer2.mp4","")
        return mutableListOf(chapter1,chapter2)
    }

    fun takeSystem(): UNQFlix = unqflixModel
}