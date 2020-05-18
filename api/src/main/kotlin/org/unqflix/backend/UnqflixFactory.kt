package org.unqflix.model

import domain.*

object UnqflixFactory {

    var unqflixModel:UNQFlix
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
        var yn= Movie(idGenerator.nextMovieId(),"Your Name","Una linda historia.",
            "www.yourname.com/poster.png",Available(),"www.yourname.com/movie.mp4",96,
            categories = mutableListOf(baseCats[1],baseCats[6]))

        var jw= Movie(idGenerator.nextMovieId(),"John Wick","El gran John",
            "www.johnwickl.com/poster.png",Available(),"www.johnwickl.com/movie.mp4",118,
            categories = mutableListOf(baseCats[3]))


        val nico = User(idGenerator.nextUserId(),"Nico Martinez","9784 5548 1123 1234",
            "https://www.nico.com.ar/image.jpg","nico@gmail.com","martinez2", mutableListOf(yn,gint,dn),
            mutableListOf(dbz,jw))

        val uli = User(idGenerator.nextUserId(),"Uli Lopez","9004 5994 0023 9875",
            "https://www.uli.com.ar/image.jpg","uli@gmail.com","uli01", mutableListOf(dbz,snk),
            mutableListOf(gint,yn,jw))

        val chester = User(idGenerator.nextUserId(),"Chester Sandoval","1234 5678 9123 9999",
            "https://www.chester.com.ar/image.jpg","chester@gmail.com","fede73", mutableListOf(opm,jw,dbz,dn),
        mutableListOf(oneP,snk))

        unqflixModel= UNQFlix(series = mutableListOf(dbz, oneP, gint, dn, snk, opm), movies = mutableListOf(yn,jw),
            categories = baseCats, users = mutableListOf(nico,uli,chester), banners = mutableListOf(jw,gint,oneP,yn))
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