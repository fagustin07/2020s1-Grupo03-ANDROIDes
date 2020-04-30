package org.unqflix.model

import domain.IdGenerator
import domain.Serie
import domain.UNQFlix
import org.uqbar.commons.model.annotations.Observable

@Observable
class UNQflixAppModel(private val model: UNQFlix) {
    var unqflixModel = model
    var filteredSeries= mutableListOf<SerieAppModel>()

    var selectedSerie: SerieAppModel? = null
    var serieToSearch: String = ""
        set(value) {
            field = value
            filtrateSeries()
        }

    init {
        unqflixModel.series.forEach { filteredSeries.add(SerieAppModel(it)) }
    }
    fun addSerie(newSerie: Serie) {
        unqflixModel.addSerie(newSerie)
        restartFilter()
    }

    fun filtrateSeries() {
        filteredSeries.removeAll(filteredSeries)
        unqflixModel.searchSeries(serieToSearch).forEach { filteredSeries.add(SerieAppModel(it)) }
    }

    fun removeSerie() {
        selectedSerie?.id?.let { unqflixModel.deleteSerie(it) }
    }

    fun restartFilter() {
        serieToSearch=""
        filtrateSeries()
    }
    fun categories(): MutableList<CategoryAppModel> {
        var categoriesInSystem = mutableListOf<CategoryAppModel>()
        unqflixModel.categories.forEach { categoriesInSystem.add(CategoryAppModel(it))}
        return categoriesInSystem
    }

    fun series(): MutableList<SerieAppModel>{
        var seriesInSystem = mutableListOf<SerieAppModel>()
        unqflixModel.series.forEach { seriesInSystem.add(SerieAppModel(it))}
        return seriesInSystem
    }


}

