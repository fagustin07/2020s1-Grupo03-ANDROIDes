package org.unqflix.view.chapter

import ICON
import org.unqflix.exceptions.ExistItemTitleException
import org.unqflix.model.ChapterAppModel
import org.uqbar.arena.kotlin.extensions.asColumns
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.thisWindow
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class EditChapterWindow(owner : WindowOwner, model : ChapterAppModel?) : ABMChapterWindow(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Editing chapter"
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
                    tryToModifyChapter()
                    thisWindow.close()
                }
            }
            Button(it) with {
                caption = "Cancel"
                onClick{
                    thisWindow.close()
                }
            }
        }
    }

    private fun tryToModifyChapter() {
        try {
            modelObject.modifyChapter()
        } catch (e: ExistItemTitleException) {
            throw UserException(e.message)
        }
    }

}