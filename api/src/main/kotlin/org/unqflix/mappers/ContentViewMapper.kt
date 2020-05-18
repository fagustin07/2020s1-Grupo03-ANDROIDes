package org.unqflix.mappers

import domain.ContentState

data class ContentViewMapper(

    val id : String,
    val title: String,
    val description : String,
    val state : String

)