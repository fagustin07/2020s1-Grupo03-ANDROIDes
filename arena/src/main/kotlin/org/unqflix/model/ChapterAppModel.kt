package org.unqflix.model

import domain.Chapter
import domain.Season
import org.unqflix.exceptions.ExistItemTitleException
import org.uqbar.commons.model.annotations.Observable

@Observable
class ChapterAppModel(chapter : Chapter, seasonWhoBelongs : Season) {

        var model = chapter
        var seasonWhoBelongs = seasonWhoBelongs

        var title = model.title
        var description = model.description
        var duration = model.duration
        var video = model.video
        var thumbnail = model.thumbnail

        private fun updateChapterFields(){
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

        fun addChapterToSystem(chapter: Chapter){
                checkTitle() //capaz borrar éste --------------------------------------------------------
                updateChapterFields()
                seasonWhoBelongs.addChapter(chapter)
        }

        fun removeChapterFromSystem(){
                seasonWhoBelongs.deleteChapter(model.id)
        }

        private fun checkTitle() {   //.toMutableList().contains(title.toLowerCase())) O //.any{it.equals(title, ignoreCase = true)})
                if (model.title != title && seasonWhoBelongs.chapters.map { it.title }.toMutableList().contains(title.toLowerCase())){
                        throw ExistItemTitleException("'$title' already exists in another season from" +
                                "'${seasonWhoBelongs.title}' season. Please, insert another title.")
                }
        }
}
