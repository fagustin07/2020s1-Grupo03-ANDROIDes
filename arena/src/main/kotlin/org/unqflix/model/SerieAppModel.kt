package org.unqflix.model

import domain.*
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.WordUtils
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class SerieAppModel(var serie : Serie,categories: MutableList<CategoryAppModel> = mutableListOf(),
                    series: MutableList<SerieAppModel> = mutableListOf()) {

    val model = serie
    var title = WordUtils.capitalize(serie.title)
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
    var serieSeasons = mutableListOf<SeasonAppModel>()

    init {
        allSeries.removeIf { it.serie==serie }

        serie.seasons.forEach{ serieSeasons.add(SeasonAppModel(it,serie))}
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

    fun addToSystem() {
        updateFields()
        UnqflixFactory.takeSystem().addSerie(serie)
    }

    fun modifySerie() {
        checkSerieTitle()
        updateFields()
    }

    fun updateFields() {
        serie.title= title.toLowerCase()
        serie.description= description
        serie.poster= poster
        serie.state=serieState()
        serie.categories=chosenCategories.map { it.category }.toMutableList()
        serie.relatedContent=chosenSeries.map { it.serie }.toMutableList()
    }

    private fun checkSerieTitle(){
        if (serie.title!=title &&allSeries.map { it.title }.any{it.equals(title,ignoreCase = true)}){
            throw ExistItemTitleException("Serie called '${title.toUpperCase()}' already exists in the system.\n" +
                    " Please, insert another title!")
        }
    }

    fun updateSeasonsList() {
        serieSeasons.removeAll(serieSeasons)
        model.seasons.forEach{ serieSeasons.add(SeasonAppModel(it,serie))}

    }

    fun removeFromSystem() {
        UnqflixFactory.takeSystem().deleteSerie(id())
    }

    fun seasons()=serie.seasons
    fun state() = if(serie.state::class == Available::class) "✓" else "✘"
    fun id()= serie.id
    fun seasonsSize() = serie.seasons.size
    fun idAndTitle()= "${serie.id} - $title"

}


