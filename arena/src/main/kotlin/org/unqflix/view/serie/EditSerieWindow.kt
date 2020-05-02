package org.unqflix.view.serie

import ICON
import org.unqflix.exceptions.ExistItemTitleException
import org.unqflix.model.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class EditSerieWindow(owner: WindowOwner, model: SerieAppModel?) : ABMSerieWindow(owner, model) {

    override fun createFormPanel(mainPanel: Panel?) {
        title= "Modify Serie"
        iconImage= ICON
        setMinHeight(500)
        setMinWidth(400)
        baseInformation(mainPanel)
        categoriesPanel(mainPanel)
        relatedContentPanel(mainPanel)

    }

    override fun addActions(mainPanel: Panel) {
        Button(mainPanel) with {
            caption= "Accept"
            onClick {
                tryCheckTitle()
                tryModify()
                close()
            }
        }
        Button(mainPanel) with {
            caption= "Cancel"
            onClick {
                close()
            }
        }

    }
    private fun tryModify(){
        try {
            modelObject.modifySerie()
        }catch (e: ExistItemTitleException){
            throw UserException(e.message)
        }
    }
}