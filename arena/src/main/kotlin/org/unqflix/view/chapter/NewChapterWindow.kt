package org.unqflix.view.chapter

import ICON
import domain.ExistsException
import org.unqflix.model.ChapterAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class NewChapterWindow(owner : WindowOwner, model : ChapterAppModel?) : ABMChapterWindow(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Creating new chapter"
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
                    thisWindow.close()
                }
            }
            Button(it) with {
                caption = "Cancel"
                onClick{ thisWindow.close() }
            }


        }
    }

    private fun tryToAddSystem() {
        try {
            modelObject.addChapterToSystem()
        } catch (e: ExistsException) {
            throw UserException(e.message)
        }
    }

}