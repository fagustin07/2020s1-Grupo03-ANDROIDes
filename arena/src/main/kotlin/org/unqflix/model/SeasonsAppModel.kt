package org.unqflix.model

import domain.Season
import org.uqbar.commons.model.annotations.Observable

@Observable
class SeasonsAppModel(season : Season) {

    var system = season
    var selectedChapter : ChapterAppModel? = null

    var id = system.id
    var title = system.title
    var description = system.description
    var poster = system.poster

    var chaptersSize = system.chapters.size
    lateinit var chaptersF : MutableList<ChapterAppModel>

    init {
        val chapters = mutableListOf<ChapterAppModel>()
        system.chapters.forEach {
            chapters.add(ChapterAppModel(it))}

        chaptersF = chapters
    }

    fun addChapter(chapter : ChapterAppModel){
        system.addChapter(chapter.system)
    }

    fun deleteChapter(idChapter : String) = system.deleteChapter(idChapter)



}