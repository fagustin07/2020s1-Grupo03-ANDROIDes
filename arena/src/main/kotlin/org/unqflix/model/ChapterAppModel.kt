package org.unqflix.model

import domain.Chapter
import domain.Season
import domain.Serie
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class ChapterAppModel(chapter : Chapter, seasonWhoBelongs : Season, serieWhoBelongs : Serie) {

        var model = chapter
        var seasonWhoBelongs = seasonWhoBelongs
        var serieWhoBelongs = serieWhoBelongs
        var title = model.title
        var description = model.description
        var duration = model.duration
        var video = model.video
        var thumbnail = model.thumbnail

         fun updateChapterFields(){
                model.title = title.toLowerCase().capitalize()
                model.description = description
                model.duration = duration
                model.video =  video
                model.thumbnail = thumbnail
        }

        fun modifyChapter(){
                checkTitle()
                updateChapterFields()
        }

        fun addChapterToSystem(){
                updateChapterFields()
                UnqflixFactory.takeSystem().addChapter(serieWhoBelongs.id,seasonWhoBelongs.id,model)
        }

        fun removeChapterFromSystem(){
                UnqflixFactory.takeSystem().deleteChapter(serieWhoBelongs.id,seasonWhoBelongs.id,model.id)
        }

        private fun checkTitle() {
                if (model.title != title && seasonWhoBelongs.chapters.map { it.title }.any{title.equals(it,ignoreCase = true)}){
                        throw ExistItemTitleException("'$title' already exists in another chapter from" +
                                "'${seasonWhoBelongs.title}' season. Please, insert another title.")
                }
        }

        fun minutes()= "$duration min"
}
