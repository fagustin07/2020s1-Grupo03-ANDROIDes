package org.unqflix.view.serie

import ICON
import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.model.SerieAppModel
import org.unqflix.view.serie.ABMSerieDialog
import org.uqbar.commons.model.exceptions.UserException
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.WindowOwner

class NewSerieDialog(owner: WindowOwner, model: SerieAppModel?) : ABMSerieDialog(owner, model) {

    override fun createFormPanel(mainPanel: Panel?) {
        title= "Creating new Serie"
        iconImage= ICON
        setMinHeight(500)
        setMinWidth(400)
        baseInformation(mainPanel)
        categoriesPanel(mainPanel)
        relatedContentPanel(mainPanel)

    }


    override fun addActions(p0: Panel?) {
        Button(p0) with {
            caption= "Create"
            onClick {
                try {
                    checkFields()
                    thisWindow.modelObject.updateFields()
                    close()
                    accept()
                }catch(e: EmptyFieldException){
                    throw UserException(e.message)
                }
            }
        }
        Button(p0) with {
            caption= "Cancel"
            onClick {
                close()
                cancel()
            }
        }

    }
}
