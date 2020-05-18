package org.unqflix.mappers

import domain.Available
import domain.Content
import domain.ContentState
import domain.UNQFlix

class ContentSimpleMapper(val id : String,val title : String ,val description : String ,status : ContentState) {
    val state : String = if(status::class==Available::class) "Available" else "Unavailable"
}

class ContentMapper(contentList : MutableCollection<Content>)
{
    val  backend = contentList
    fun bannersView()= mapOf("banners" to "${mapContentToList(backend)}")

    fun contentView() = mapOf("content" to "${mapContentToList(backend)}")

    private fun mapContentToList(contentList : MutableCollection<Content>) =
        contentList.map {
            mapOf(
                "id" to "${it.id}",
                "title" to "${it.title}",
                "description" to "${it.description}",
                "state" to "${stateToString(it.state)}")}

    private fun stateToString(status : ContentState) = if(status::class==Available::class) "Available" else "Unavailable"

}