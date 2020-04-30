package org.unqflix.view.serie

import ICON
import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.exceptions.ExistItemTitleException
import org.unqflix.model.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class EditSerieDialog(owner: WindowOwner, model: SerieAppModel?) : ABMSerieDialog(owner, model) {

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
            caption= "Modify"
            onClick {
                try {
                    checkFields()
                }catch(e: EmptyFieldException){
                    throw UserException(e.message)
                }
                tryToModify()
                close()
                accept()
            }
        }
        Button(mainPanel) with {
            caption= "Cancel"
            onClick {
                close()
                cancel()
            }
        }

    }
    private fun tryToModify(){
        try {
            modelObject.modifySerie()
        }catch (e: ExistItemTitleException){
            throw UserException(e.message)
        }
    }
}