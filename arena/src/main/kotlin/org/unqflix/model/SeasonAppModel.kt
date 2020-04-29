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
    lateinit var chaptersF: MutableList<ChapterAppModel>

    init {
        val chapters = mutableListOf<ChapterAppModel>()
        model.chapters.forEach {
            chapters.add(ChapterAppModel(it))
        }

        chaptersF = chapters
    }


    fun modifySeason() {
        id = id
        model.title = title
        model.description = description
        model.poster = poster
    }


    fun addChapter(chapter: ChapterAppModel) {
        model.addChapter(chapter.system)
    }

    fun deleteChapter(idChapter: String) = model.deleteChapter(idChapter)


}