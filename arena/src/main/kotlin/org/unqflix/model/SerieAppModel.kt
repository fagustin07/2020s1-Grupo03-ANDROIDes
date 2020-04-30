package org.unqflix.model

import domain.*
import org.unqflix.exceptions.ExistSeasonTitleException
import org.unqflix.exceptions.ExistSerieTitleException
import org.uqbar.arena.kotlin.extensions.thisWindow
import org.uqbar.commons.model.annotations.Observable

@Observable
class SerieAppModel(var serie : Serie,categories: MutableList<CategoryAppModel> = mutableListOf(),
                    series: MutableList<SerieAppModel> = mutableListOf()) {

    val model = serie
    val id = serie.id
    var idAndTitle= "${serie.id} - ${serie.title}"
    var title = serie.title
        set(value){
            field=value.toLowerCase()
        }
    var poster = serie.poster
    var description= serie.description
    var status = serie.state::class == Available::class
    var state: String = if(serie.state::class == Available::class){
        "✓"
    }else{
        "✘"
    }
    var serieSelected : SerieAppModel? = null
    var relatedSerieToRemove: SerieAppModel? = null
    var categorySelected : CategoryAppModel? = null
    var categoryToRemove: CategoryAppModel? = null
    var seasonSelected : SeasonAppModel? = null
    var allSeries = series
    var systemCategories = categories
    var chosenSeries = mutableListOf<SerieAppModel>()
    var chosenCategories = mutableListOf<CategoryAppModel>()
    var seasonsF = mutableListOf<SeasonAppModel>()
    var seasonsSize = serie.seasons.size
    init {
        allSeries.removeIf { it.serie==serie }
        allSeries.sortBy { id }

        serie.seasons.forEach{ seasonsF.add(SeasonAppModel(it))}
        allSeries.forEach { if (serie.relatedContent.contains(it.serie)) chosenSeries.add(it) }
        systemCategories.forEach { if (serie.categories.contains(it.category)) chosenCategories.add(it) }

    }

    fun addCategory(){
        if(!chosenCategories.contains(categorySelected)) {
            categorySelected?.let { chosenCategories.add(it) }
        }
    }
    fun removeCategory(){
        chosenCategories.remove(categoryToRemove)
    }
    fun addRelatedContent(){
        if(!chosenSeries.contains(serieSelected)) {
            serieSelected?.let { chosenSeries.add(it) }
        }
    }
    fun removeRelatedContent(){
        chosenSeries.remove(relatedSerieToRemove)
    }

    fun serieState()= if (status) Available() else Unavailable()

    fun updateFields() {
        serie.title=title
        serie.description=description
        serie.poster=poster
        serie.state=serieState()
        serie.categories=chosenCategories.map { it.category }.toMutableList()
        serie.relatedContent=chosenSeries.map { it.serie }.toMutableList<Content>()
    }

    fun modifySerie() {
        checkSerieTitle()
        updateFields()
    }
    private fun checkSerieTitle(){
        if (serie.title!=title &&allSeries.map { it.title }.toList().contains(title)){
            throw ExistSerieTitleException(
                "La serie $title ya se encuentra en el sistema. Por favor, introduzca otro nombre," +
                        "o bien, seleccione la que desea modificar en el menu principal.")
        }
    }

    fun deleteSeason(idSeason : String){
        model.deleteSeason(idSeason)
    }

    fun addSeasonToSystem(season : Season){
        model.addSeason(season)
        update()

    }

    fun update() {
        seasonsF.removeAll(seasonsF)
        model.seasons.forEach{ seasonsF.add(SeasonAppModel(it))}
        seasonSelected = null
    }
}


