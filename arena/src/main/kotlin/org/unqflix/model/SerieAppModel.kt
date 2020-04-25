package org.unqflix.model

import domain.*
import org.uqbar.commons.model.annotations.Observable

@Observable
class SerieAppModel(var serie : Serie) {

    val model = serie
    val id = serie.id
    var title = serie.title
    var state: String = if(serie.state::class == Available::class){
        "✓"
    }else{
        "✘"
    }
    var seasons = serie.seasons.size

}

