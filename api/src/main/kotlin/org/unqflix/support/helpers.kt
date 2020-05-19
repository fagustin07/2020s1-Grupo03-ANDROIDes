package org.unqflix.support

import domain.Content
import org.unqflix.mappers.ContentSimpleMapper


fun generateMessage(typeMessage: String, descriptionMessage: String)=
    mapOf("result" to typeMessage, "description" to descriptionMessage)

fun generateContentView(contentList: MutableCollection<Content>)=
    contentList.map { ContentSimpleMapper(it.id,it.title,it.description,it.state) }.toMutableList()