package org.unqflix.support

import domain.Available
import domain.Content
import org.unqflix.mappers.ContentMapper


fun generateMessage(typeMessage: String, descriptionMessage: String)=
    mapOf("result" to typeMessage, "description" to descriptionMessage)

fun generateContentView(contentList: MutableCollection<Content>)=
    contentList.map { ContentMapper(it.id,it.title,it.description,it.state::class== Available::class) }.toMutableList()