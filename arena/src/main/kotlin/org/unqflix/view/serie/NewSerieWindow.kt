package org.unqflix.view.serie

import ICON
import domain.ExistsException
import org.unqflix.model.SerieAppModel
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class NewSerieWindow(owner: WindowOwner, model: SerieAppModel?) : ABMSerieWindow(owner, model) {

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
                tryCheckTitle()
                tryAddToSystem()
                close()
            }
        }
        Button(p0) with {
            caption= "Cancel"
            onClick {
                close()
            }
        }

    }

    private fun tryAddToSystem() {
        try {
            modelObject.addToSystem()
        } catch (e: ExistsException) {
            throw UserException(e.message)
        }
    }
}
