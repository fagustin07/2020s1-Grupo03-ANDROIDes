package org.unqflix.mappers

import domain.Available
import domain.ContentState

class ContentMapper(val id : String, val title: String, val description : String, status : ContentState)
{
    val state: String = if(status::class==Available::class) "Available" else "Unavailable"
}