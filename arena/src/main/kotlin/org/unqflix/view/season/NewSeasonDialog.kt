package org.unqflix.view.season

import ICON
import domain.ExistsException
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
                    tryToCheckEmptyTitle()
                    tryToAddSystem()
                    close()
                }
            }
            Button(it) with {
                caption = "Cancel"
                onClick{ close()}
            }


        }
    }

    private fun tryToAddSystem() {
        try {
            modelObject.addToSystem()
        } catch (e: ExistsException) {
            throw UserException(e.message)
        }
    }

}