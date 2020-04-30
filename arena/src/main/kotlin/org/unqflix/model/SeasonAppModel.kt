package org.unqflix.model

import domain.Season
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class SeasonAppModel(season: Season, serieFromSeason : MutableList<Season> = mutableListOf()) {
    var model = season

    var id = model.id
    var title = season.title
    var description = season.description
    var poster = season.poster
    var serieFromSeason = serieFromSeason
    var chaptersSize = model.chapters.size
    var chaptersF= mutableListOf<ChapterAppModel>()

    init {
        model.chapters.forEach { chaptersF.add(ChapterAppModel(it)) }
    }

    fun modifySeason() {
        checkTitle()
        model.title = title.toLowerCase()
        model.description = description
        model.poster = poster
    }

    private fun checkTitle() {
        if (model.title!=title && serieFromSeason.map { it.title }.toMutableList().contains(title.toLowerCase())){
            throw ExistItemTitleException("El titulo '$title' ya existe en la serie seleccionada, por favor, elija otro nombre.")
        }
    }


    fun addChapter(chapter: ChapterAppModel) {
        model.addChapter(chapter.system)
    }

    fun deleteChapter(idChapter: String) = model.deleteChapter(idChapter)


}