package org.unqflix.backend

import domain.*

object UnqflixFactory {

    var unqflixModel:UNQFlix
    val idGenerator = IdGeneratorFactory.takeIdGen()



    init {
        val idGenerator = IdGeneratorFactory.takeIdGen()
        var baseCats = baseCategories()

        var oneP = Serie(
            idGenerator.nextSerieId(), "one piece", "las aventuras de los Mugiwara!",
            "https://images-na.ssl-images-amazon.com/images/I/61j4P7FpQhL.jpg", Available(), mutableListOf(baseCats[1],baseCats[3])
        )
        var gint = Serie(
            idGenerator.nextSerieId(), "gintama", "las aventuras de Gintoki Sakata",
            "https://images-na.ssl-images-amazon.com/images/I/61yd4MxxPGL._AC_.jpg", Unavailable(), mutableListOf(baseCats[0],baseCats[1])
        )
        var dn = Serie(
            idGenerator.nextSerieId(),"death note","el prota encuentra una libreta magica que mata gente",
            "https://images-na.ssl-images-amazon.com/images/I/515es5ofRxL._AC_.jpg", Unavailable(),
            mutableListOf(baseCats[1])
        )
        var snk = Serie(
            idGenerator.nextSerieId(),"attack on titans","Un mundo en donde los titanes cazan personas.",
            "https://otakuwagon.com/wp-content/uploads/2020/04/s592.jpg", Available(), mutableListOf(baseCats[1])
        )
        var opm = Serie(
            idGenerator.nextSerieId(), "one punch man", "Saitama es el hombre mas fuerte del mundo",
            "https://images-na.ssl-images-amazon.com/images/I/717aat3l-YL._AC_SL1224_.jpg",Available(),mutableListOf(baseCats[1])
        )
        var dbz = Serie(
            idGenerator.nextSerieId(),"dragon ball z","Goku quiere ser el mas fuerte del mundo",
            "https://images-na.ssl-images-amazon.com/images/I/61JKxqapVQL._AC_.jpg",Available(),mutableListOf(baseCats[1],baseCats[4]), baseSeasons(),
            mutableListOf(opm,dn,gint)
        )

        var yn= Movie(idGenerator.nextMovieId(),"your name","Una linda historia.",
            "https://images-na.ssl-images-amazon.com/images/I/71-WBN3FCBL._AC_SY741_.jpg",Available(),"www.yourname.com/movie.mp4",112,
            categories = mutableListOf(baseCats[1],baseCats[6]))

        var jw= Movie(idGenerator.nextMovieId(),"john wick","El gran John",
            "https://images-na.ssl-images-amazon.com/images/I/71WiYBT2QsL._AC_SY741_.jpg",Available(),"johnwick.com/movie.mp4",107,
            categories = mutableListOf(baseCats[3]))

        var delr= Movie(idGenerator.nextMovieId(),"y donde estan las rubias?","Los detectives que se visten " +
                "de mujeres para cumplir su mision",
            "https://i.pinimg.com/originals/c4/ad/6b/c4ad6b40be573f8cf2c7459447318f7d.jpg",Available(),"www.johnwickl.com/movie.mp4",115,
            mutableListOf("Shawn Wayans", "Marlon Wayans","John Heard"), mutableListOf("Keenen Ivory Wayans"),
            mutableListOf(baseCats[0]), mutableListOf(yn,jw))


        val nico = User(idGenerator.nextUserId(),"Nico Martinez","9784 5548 1123 1234",
            "https://www.nico.com.ar/image.jpg","nico@gmail.com","Martinez2", mutableListOf(yn,gint,dn),
            mutableListOf(dbz,jw))

        val uli = User(idGenerator.nextUserId(),"Uli Lopez","9004 5994 0023 9875",
            "https://www.uli.com.ar/image.jpg","uli@gmail.com","Uli01", mutableListOf(dbz,snk),
            mutableListOf(gint,yn,jw))

        val chester = User(idGenerator.nextUserId(),"Chester Sandoval","1234 5678 9123 9999",
            "https://www.chester.com.ar/image.jpg","chester@gmail.com","Fede73", mutableListOf(opm,jw,dbz,dn),
        mutableListOf(oneP,snk))

        unqflixModel = UNQFlix(series = mutableListOf(oneP, gint, dn, snk, opm, dbz), movies = mutableListOf(yn,jw,delr),
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
                    " y deciden ir a Namekusei.",60,"dragonballz.com/sagadefreezer1.mp4","")
        var chapter2 = Chapter(
            idGenerator.nextChapterId(),"El misterio de yunzabit!","La busqueda de la nave espacial de Dios.",
            90,"dragonballz.com/sagadefreezer2.mp4","")
        return mutableListOf(chapter1,chapter2)
    }

    fun takeSystem(): UNQFlix = unqflixModel
}