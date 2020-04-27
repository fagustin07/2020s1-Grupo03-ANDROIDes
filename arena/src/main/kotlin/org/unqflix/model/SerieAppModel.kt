package org.unqflix.model

import domain.*
import org.uqbar.commons.model.annotations.Observable

@Observable
class SerieAppModel(var serie : Serie) {

    val model = serie
    var seasonSelected : SeasonsAppModel? = null

    val id = serie.id
    var title = serie.title
    var state: String = if(serie.state::class == Available::class){
        "✓"
    }else{
        "✘"
    }
    lateinit var seasonsF : MutableList<SeasonsAppModel>
    var seasonsSize = serie.seasons.size

    init {
        val seasons = mutableListOf<SeasonsAppModel>()
        model.seasons.forEach {
            seasons.add(SeasonsAppModel(it))}

            seasonsF = seasons
    }


    fun addSeason(season : SeasonsAppModel){
        model.addSeason(season.system)
    }

    fun deleteSeason(idSeason : String){
        model.deleteSeason(idSeason)
    }

    fun addChapter(id : String, chapter : ChapterAppModel){
        model.addChapter(id,chapter.system)
    }

    fun deleteChapter(idS : String, idC : String) {
        model.deleteChapter(idS,idC)
    }


}


