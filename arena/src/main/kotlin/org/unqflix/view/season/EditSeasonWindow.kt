package org.unqflix.view.season

import ICON
import org.unqflix.exceptions.ExistItemTitleException
import org.unqflix.model.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class EditSeasonWindow(owner : WindowOwner, model : SeasonAppModel?) : ABMSeasonWindow(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Editing season"
        iconImage= ICON

        baseInformation(mainPanel)
    }

    override fun addActions(actionsPanel: Panel) {
        Panel(actionsPanel) with {
            asColumns(2)
            Button(it) with {
                caption = "Save"
                onClick {
                    tryToCheckEmptyTitle()
                    tryToModifySeason()
                    close()
                }
            }
            Button(it) with {
                caption = "Cancel"
                onClick{
                    close()
                }
            }
        }
    }

    private fun tryToModifySeason() {
        try {
            modelObject.modifySeason()
        } catch (e: ExistItemTitleException) {
            throw UserException(e.message)
        }
    }

}