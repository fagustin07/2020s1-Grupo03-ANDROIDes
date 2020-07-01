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
            "https://images-na.ssl-images-amazon.com/images/I/71-WBN3FCBL._AC_SY741_.jpg",Available(),"https://storage.googleapis.com/omega-pivot-269518.appspot.com/cv5311514lt.mp4",112,
            categories = mutableListOf(baseCats[1],baseCats[6]))

        var jw= Movie(idGenerator.nextMovieId(),"john wick","El gran John",
            "https://images-na.ssl-images-amazon.com/images/I/71WiYBT2QsL._AC_SY741_.jpg",Available(),"https://repelisgoo.net/srv-pv/crypto/b/v9Aj-7qcRcW2VA4y5ovdt8aRuCG4h2dl0Be7nH_cOLFlu1IVcr6agIIWyoLLKoQagRipSSznmVoo8usOPvhKRftaICChBVV9TWSBzwy8MM7EP6MpguWx7O8YC0NkCiE9F20tG4V3aNw3ojpVZgmWDIrGDyuHO-Ip36Ped6vDGmg_1yHTmsvkEHTrCIvI-xJjkO9Ji4ar5mubtxaiNVtFCw/382320C8467040D3192DA76EFA6189C3670F9FDC1699DBFA32E9865779ADBB4E?qs=1&youtubeTrailer=UNIv8nzUryo",107,
            categories = mutableListOf(baseCats[3]))

        var delr= Movie(idGenerator.nextMovieId(),"y donde estan las rubias?","Los detectives que se visten " +
                "de mujeres para cumplir su mision",
            "https://i.pinimg.com/originals/c4/ad/6b/c4ad6b40be573f8cf2c7459447318f7d.jpg",Available(),"https://fvs.io/redirector?token=cU56RDBQWGFuREJVSVZhbkVaU2s2bU1xbDVjUCtvaGJTQnZZQll6K2V0djBVMlJXNUF1REVkU2kwcUUzMTNEbkM2VmxUSDYxK01PbHZkTlFKdzg1WWhUcStTSHhJcUJwYWxHaVpqQWhsZFNuNzZsUW1pQWRrdXdRTFV0RFEvMC84dGgyalhFenFmSmo4Wk8wOHB0T2VubmZFZEpCb1E9PTptUkhuVHhYN1ZkVW1LNkJRc3BOZlpnPT0",115,
            mutableListOf("Shawn Wayans", "Marlon Wayans","John Heard"), mutableListOf("Keenen Ivory Wayans"),
            mutableListOf(baseCats[0]), mutableListOf(yn,jw))


        val nico = User(idGenerator.nextUserId(),"Nico Martinez","9784 5548 1123 1234",
            "https://www.nico.com.ar/image.jpg","nico@gmail.com","Martinez2", mutableListOf(yn,gint,dn),
            mutableListOf(dbz,jw))

        val uli = User(idGenerator.nextUserId(),"Uli Lopez","9004 5994 0023 9875",
            "https://www.uli.com.ar/image.jpg","uli@gmail.com","Uli01", mutableListOf(dbz,snk),
            mutableListOf(gint,yn,jw))

        val chester = User(idGenerator.nextUserId(),"Chester Sandoval","1234 5678 9123 9999",
            "https://www.chester.com.ar/image.jpg","chester@gmail.com","Fede73", mutableListOf(opm,jw,dbz,dn,oneP,gint,snk,yn,delr),
        mutableListOf(oneP,snk))

        unqflixModel = UNQFlix(series = mutableListOf(oneP, gint, dn, snk, opm, dbz), movies = mutableListOf(yn,jw,delr),
            categories = baseCats, users = mutableListOf(nico,uli,chester), banners = mutableListOf(jw,gint,oneP,yn,opm))
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
                    " y deciden ir a Namekusei.",60,"https://videos2.sendvid.com/8c/86/nceb2qe3.mp4?validfrom=1593642256&validto=1593649456&rate=200k&burst=1000k&ip=201.176.192.99&hash=xSwERa%2FjnHCafzdxdXqPciN4PDo%3D","")
        var chapter2 = Chapter(
            idGenerator.nextChapterId(),"El misterio de yunzabit!","La busqueda de la nave espacial de Dios.",
            90,"https://videos2.sendvid.com/3d/79/a1lscyw0.mp4?validfrom=1593642368&validto=1593649568&rate=200k&burst=1000k&ip=201.176.192.99&hash=2mdGCMdKqHgwMFTnWD6Q5qYq82I%3D","")
        return mutableListOf(chapter1,chapter2)
    }

    fun takeSystem(): UNQFlix = unqflixModel
}