package org.unqflix.model

import domain.Chapter
import domain.Season
import domain.Serie
import org.apache.commons.lang.WordUtils
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class SeasonAppModel(season: Season, serieWhoBelongs : Serie) {

    var model = season
    var serieWhoBelongs = serieWhoBelongs
    var chapterSelected : ChapterAppModel? = null
    var title = WordUtils.capitalize(model.title.toLowerCase())
    var description = season.description
    var poster = season.poster
    var seasonChapters= mutableListOf<ChapterAppModel>()
    init {
        model.chapters.forEach { seasonChapters.add(ChapterAppModel(it,model)) }
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
        if (model.title != title && serieWhoBelongs.seasons.map {it.title}.any{it.equals(title, ignoreCase = true)}){
            throw ExistItemTitleException("'$title' already exists in another season from" +
                    "'${serieWhoBelongs.title}' serie. Please, insert another title.")
        }
    }

    fun updateChapterList() {
        seasonChapters.removeAll(seasonChapters)
        model.chapters.forEach{ seasonChapters.add(ChapterAppModel(it,model))}

    }

    fun deleteFromSystem() {
        UnqflixFactory.takeSystem().deleteSeason(serieWhoBelongs.id,id())
    }

    fun addChapter(chapter: Chapter) {
        UnqflixFactory.takeSystem().addChapter(serieWhoBelongs.id,model.id,chapter)
    }


    fun deleteChapter(){
        chapterSelected?.model?.id?.let { UnqflixFactory.takeSystem().deleteChapter(serieWhoBelongs.id,id(), it) }
    }
    fun id() = model.id

    fun chaptersSize() = model.chapters.size


}
