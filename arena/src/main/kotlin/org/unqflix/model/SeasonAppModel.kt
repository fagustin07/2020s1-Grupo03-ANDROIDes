package org.unqflix.model

import domain.Season
import domain.Serie
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class SeasonAppModel(season: Season, serieWhoBelongs : Serie) {

    var model = season
    var serieWhoBelongs = serieWhoBelongs
    var title = season.title
    var description = season.description
    var poster = season.poster
    var seasonChapters= mutableListOf<ChapterAppModel>()
    init {
        model.chapters.forEach { seasonChapters.add(ChapterAppModel(it)) }
    }

    fun addToSystem() {
        updateSeasonFields()
        UnqflixFactory.takeSystem().addSeason(serieWhoBelongs.id, model)
    }

    fun modifySeason() {
        checkTitle()
        updateSeasonFields()
    }

    private fun updateSeasonFields() {
        model.title = title.toLowerCase()
        model.description = description
        model.poster = poster
    }

    private fun checkTitle() {
        if (model.title!=title && serieWhoBelongs.seasons.map { it.title }.toMutableList().contains(title.toLowerCase())){
            throw ExistItemTitleException("'$title' already exists in another season from" +
                    "'${serieWhoBelongs.title}' serie. Please, insert another title.")
        }
    }

    fun addChapter(chapter: ChapterAppModel) {}

    fun deleteChapter(idChapter: String){}


    fun id() = model.id
    fun chaptersSize() = model.chapters.size

    fun deleteFromSystem() {
        UnqflixFactory.takeSystem().deleteSeason(serieWhoBelongs.id,id())
    }


}
