package org.unqflix.view

import ICON
import org.unqflix.model.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action

class SeasonNewDialog(owner : WindowOwner, model : SeasonAppModel?) : Dialog<SeasonAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Creating new Serie"
        iconImage= ICON

        baseInformation(mainPanel)
    }

    private fun baseInformation(mainPanel: Panel){
        GroupPanel(mainPanel) with {
            title = "Season info"
            asColumns(2)
            Label(it) withText ("Title: ")
            TextBox(it) with {
                width=100
                bindTo("title")
            }
            Label(it) withText ("Description: ")
            TextBox(it) with {
                width = 100
                height = 60
                bindTo("description")
            }
            Label(it) withText ("Poster: ")
            TextBox(it) with {
                width= 100
                bindTo("poster")
            }

        }

    }

    override fun addActions(actionsPanel: Panel) {
        Panel(actionsPanel) with {
            asColumns(2)
            Button(it) with {
                caption = "Save"
                onClick(Action { thisWindow.modelObject.modifySeason() ; close() ; accept()})
            }
            Button(it) with {
                caption = "Cancel"
                onClick(Action { close() ; cancel() })
            }


        }
    }


}