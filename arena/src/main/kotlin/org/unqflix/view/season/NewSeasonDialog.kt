package org.unqflix.view.season

import ICON
import domain.ExistsException
import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.model.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class NewSeasonDialog(owner : WindowOwner, model : SeasonAppModel?) : ABMSeasonDialog(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Creating new Season"
        iconImage= ICON

        baseInformation(mainPanel)
    }

    override fun addActions(actionsPanel: Panel) {
        Panel(actionsPanel) with {
            asColumns(2)
            Button(it) with {
                caption = "Save"
                onClick{
                    try {
                        checkFields()
                   }catch(e: EmptyFieldException){
                       throw UserException(e.message)
                    }
                    thisWindow.modelObject.modifySeason()
                    try {
                        accept()
                    }catch (e : ExistsException){
                        throw UserException(e.message)
                    }
                    close()
                }
            }
            Button(it) with {
                caption = "Cancel"
                onClick{ close()}
            }


        }
    }

}