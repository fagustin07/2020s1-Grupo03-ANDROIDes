package org.unqflix.model

import domain.*
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class SerieAppModel(var serie : Serie,categories: MutableList<CategoryAppModel> = mutableListOf(),
                    series: MutableList<SerieAppModel> = mutableListOf()) {

    val model = serie
    val id = serie.id
    var title = serie.title
    var poster = serie.poster
    var description= serie.description
    var status = serie.state::class == Available::class
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
        serie.title=title.toLowerCase()
        serie.description=description
        serie.poster=poster
        serie.state=serieState()
        serie.categories=chosenCategories.map { it.category }.toMutableList()
        serie.relatedContent=chosenSeries.map { it.serie }.toMutableList()
    }

    fun modifySerie() {
        checkSerieTitle()
        updateFields()
    }

    private fun checkSerieTitle(){
        if (serie.title!=title &&allSeries.map { it.title }.toList().contains(title)){
            throw ExistItemTitleException(
                "La serie $title ya se encuentra en el sistema. Por favor, introduzca otro nombre," +
                        "o bien, seleccione la que desea modificar en el menu principal.")
        }
    }

    fun addSeasonToSystem(season : Season){
        model.addSeason(season)
        updateSeasonsList()

    }
    fun seasons()=serie.seasons

    fun updateSeasonsList() {
        seasonsF.removeAll(seasonsF)
        model.seasons.forEach{ seasonsF.add(SeasonAppModel(it))}
        seasonSelected = null
    }
    fun deleteSeason(){
        seasonSelected?.id?.let { model.deleteSeason(it) }
        updateSeasonsList()
    }


    fun state() = if(serie.state::class == Available::class) "✓" else "✘"

    fun seasonsSize() = serie.seasons.size

    fun idAndTitle()= "${serie.id} - ${serie.title}"

}


