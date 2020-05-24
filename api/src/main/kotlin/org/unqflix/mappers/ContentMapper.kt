package org.unqflix.mappers

import domain.Season

data class ContentMapper(val id : String, val title : String, val description : String, val status : Boolean)

data class MovieMapper(val id : String, val title : String, val status:Boolean, val description : String,
                       val poster:String, val video:String,val duration:Int,val actors:List<String>,val directors:List<String>,
                       val categories:List<String>,val relatedContent:List<ContentMapper>)

data class SerieMapper(
    val id: String, val title: String, val status:Boolean,val description: String, val poster:String,
    val categories: MutableList<String>, val seasons: MutableList<Season>, val relatedContent:MutableList<ContentMapper>
)

data class IdContentMapper(val id : String?)
