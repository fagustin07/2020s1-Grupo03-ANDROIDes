package org.unqflix.view.chapter

import ICON
import org.unqflix.exceptions.EmptyTitleException
import org.unqflix.model.ChapterAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException


abstract class ABMChapterWindow (owner : WindowOwner, model : ChapterAppModel?) : SimpleWindow<ChapterAppModel>(owner,model){

    override fun createFormPanel(mainPanel: Panel) {
        title= "Editing season"
        iconImage= ICON

        baseInformation(mainPanel)
    }


    fun baseInformation(mainPanel: Panel){
        GroupPanel(mainPanel) with {
            title = "Chapter info"
            asColumns(2)
            Label(it) withText ("Title: ")
            TextBox(it) with {
                width=100
                bindTo("title")
            }
            Label(it) withText ("Description: ")
            KeyWordTextArea(it) with {
                width = 100
                height = 60
                bindTo("description")
            }
            Label(it) withText ("Duration in minutes: ")
            Spinner(it) with {
                width = 100
                minValue = 1
                maxValue = 240
                bindTo("duration")
            }
            Label(it) withText ("Thumbnail: ")
            TextBox(it) with {
                width= 100
                bindTo("thumbnail")
            }
            Label(it) withText ("Video: ")
            TextBox(it) with {
                width= 100
                bindTo("video")
            }

        }

    }

    fun tryToCheckEmptyTitle() {
        try {
            checkTitleChapter()
        } catch (e: EmptyTitleException) {
            throw UserException(e.message)
        }
    }

    private fun checkTitleChapter(){
        if(modelObject.title == "" || modelObject.title.first()== ' '){
            throw EmptyTitleException("Title field cannot be empty or start with a space.\n Please, try again.")
        }
    }
}