package org.unqflix.view.season

import ICON
import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.model.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

abstract class ABMSeasonWindow(owner : WindowOwner, model : SeasonAppModel?) : SimpleWindow<SeasonAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Editing season"
        iconImage= ICON

        baseInformation(mainPanel)
    }

     fun baseInformation(mainPanel: Panel){
        GroupPanel(mainPanel) with {
            title = "Season info"
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
            Label(it) withText ("Poster: ")
            TextBox(it) with {
                width= 100
                bindTo("poster")
                withFilter { event -> event.potentialTextResult.matches(Regex("[/^a-zA-Z\\d\\-.!*?]*")) }
            }

        }

    }

    fun tryToCheckEmptyTitle() {
        try {
            checkTitleSeason()
        } catch (e: EmptyFieldException) {
            throw UserException(e.message)
        }
    }

     private fun checkTitleSeason(){
        if(modelObject.title == "" || modelObject.title.first()== ' '){
            throw EmptyFieldException("Field 'Title' cannot be empty or start with a space.\n Please, try again.")
        }
    }

}

