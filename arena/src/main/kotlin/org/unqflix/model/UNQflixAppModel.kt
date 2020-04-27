package org.unqflix.model

import domain.IdGenerator
import domain.UNQFlix
import org.uqbar.commons.model.annotations.Observable

@Observable
class UNQflixAppModel(private val model: UNQFlix, val idGenerator: IdGenerator) {
    var system = model
    lateinit var filteredSeries: MutableList<SerieAppModel>

    var selectedSerie: SerieAppModel? = null
    var serieToSearch: String = ""
        set(value) {
            field = value
            filtrateSeries()
        }

    init {
        val series = mutableListOf<SerieAppModel>()
        system.series.forEach {
            series.add(SerieAppModel(it))

            filteredSeries = series
        }
    }

    fun filtrateSeries() {
        filteredSeries.removeAll(filteredSeries)
        system.searchSeries(serieToSearch).forEach { filteredSeries.add(SerieAppModel(it)) }
    }

    fun removeSerie() {
        selectedSerie?.id?.let { system.deleteSerie(it) }
    }

    fun restartFilter() {
        serieToSearch=""
        filtrateSeries()
    }


}

