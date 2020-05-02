package org.unqflix.model

import domain.Chapter
import domain.Season
import org.apache.commons.lang.WordUtils
import org.unqflix.exceptions.ExistItemTitleException

class ChapterAppModel(chapter : Chapter, seasonWhoBelongs : Season) {

        var model = chapter
        var seasonWhoBelongs = seasonWhoBelongs

        var title = model.title
        var descrption = model.description
        var duration = model.duration
        var video = model.video
        var thumbnail = model.thumbnail

        private fun updateChapterFields(){
                model.title = title.toLowerCase().capitalize()
                model.description = descrption
                model.duration = duration
                model.video =  video
                model.thumbnail = thumbnail
        }

        fun modifyChapter(){
                checkTitle()
                updateChapterFields()
        }

        fun addChapterToSystem(chapter: Chapter){
                seasonWhoBelongs.addChapter(chapter)
        }

        fun removeChapterFromSystem(){
                seasonWhoBelongs.deleteChapter(model.id)
        }

        private fun checkTitle() {
                if (model.title!=title && seasonWhoBelongs.chapters.map { it.title }.toMutableList().contains(title.toLowerCase())){
                        throw ExistItemTitleException("'$title' already exists in another season from" +
                                "'${seasonWhoBelongs.title}' serie. Please, insert another title.")
                }
        }
}
