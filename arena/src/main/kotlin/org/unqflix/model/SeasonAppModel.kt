package org.unqflix.model

import domain.Season
import org.uqbar.commons.model.annotations.Observable

@Observable
class SeasonAppModel(season: Season) {
    var model = season

    var id = model.id
    var title = season.title
    var description = season.description
    var poster = season.poster


    var chaptersSize = model.chapters.size
    var chaptersF= mutableListOf<ChapterAppModel>()

    init {
        model.chapters.forEach { chaptersF.add(ChapterAppModel(it)) }
    }


    fun modifySeason() {
        model.title = title
        model.description = description
        model.poster = poster
    }


    fun addChapter(chapter: ChapterAppModel) {
        model.addChapter(chapter.system)
    }

    fun deleteChapter(idChapter: String) = model.deleteChapter(idChapter)


}