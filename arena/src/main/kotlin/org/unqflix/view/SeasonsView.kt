package org.unqflix.view

import ICON
import org.unqflix.model.EditSerieAppModel
import org.unqflix.model.NewSerieAppModel
import org.unqflix.model.SeasonsAppModel
import org.unqflix.model.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class SeasonsView(owner : WindowOwner, model : SerieAppModel) : SimpleWindow<SerieAppModel>(owner,model) {


    override fun createFormPanel(mainPanel: Panel) {
        title= ""
        iconImage= ICON
        Label(mainPanel) with { text = thisWindow.modelObject.title }
        makeTableOfSeasons(mainPanel)
    }

    fun makeTableOfSeasons(mainPanel : Panel){
        table<SeasonsAppModel>(mainPanel) with {
            visibleRows = 7
            bindItemsTo("seasonsF")
            bindSelectionTo("seasonSelected")
            column {
                title = "Seasons"
                fixedSize=150
                alignCenter()
                bindContentsTo("title")
            }
            column {
                title = "#Chapters"
                alignCenter()
                bindContentsTo("chaptersSize")
            }
        }

    }
    override fun addActions(mainPanel: Panel) {
        Panel(mainPanel) with {
            asColumns(2)
            Button(it) with {
                caption = "Add new season"
                onClick { }
            }
            Button(it) with {
                caption = "Modify Season"
                onClick {}
            }
            Button(it) with {
                caption = "Show chapters"
                onClick { }
            }

        }


    }

}