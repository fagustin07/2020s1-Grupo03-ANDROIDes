package org.unqflix.model

import domain.Chapter

class ChapterAppModel(chapter : Chapter) {

    var system = chapter

    var title = system.title
    var descrption = system.description
    var duration = system.duration
    var video = system.video
    var thumbnail = system.thumbnail

}
