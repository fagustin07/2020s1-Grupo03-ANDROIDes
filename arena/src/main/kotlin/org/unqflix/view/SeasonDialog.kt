package org.unqflix.view

import ICON
import data.idGenerator
import domain.Season
import org.unqflix.model.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action

class SeasonDialog(owner : WindowOwner, model : SerieAppModel) : Dialog<SerieAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Seasons"
        iconImage= ICON
        Label(mainPanel) with { text = thisWindow.modelObject.title }

        makeTableOfSeasons(mainPanel)
    }

    fun makeTableOfSeasons(mainPanel : Panel){
        table<SeasonAppModel>(mainPanel) with {
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
    override fun addActions(actionsPanel: Panel) {
        Panel(actionsPanel) with {
            asColumns(2)
            Button(it) with {
                caption = "Add new season"
                onClick {
                    close()
                    val newSeason = newSeason()
                    SeasonNewDialog(owner, SeasonAppModel(newSeason)) with {
                        onAccept { addSeasonToSystem(newSeason); reopenSeasonWindow() }
                        onCancel { reopenSeasonWindow() }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Modify Season"
                onClick {
                    close()
                    SeasonEditDialog(
                        owner, thisWindow.modelObject.seasonSelected?.model?.let
                        { season -> SeasonAppModel(season) }) with {
                        onAccept { reopenSeasonWindow(); updateData() }
                        onCancel { reopenSeasonWindow() }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Show chapters"
                onClick { }
            }
            Button(it) with {
                caption = "Back"
                onClick (Action{ close() ; cancel()})
            }

        }
    }

        fun reopenSeasonWindow(){
            SeasonDialog(owner,modelObject).open()
        }


        fun newSeason() = Season(idGenerator.nextSeasonId(),"","","")

    fun addSeasonToSystem(season: Season){
        modelObject.addSeasonToSystem(SeasonAppModel(season))
        reopenSeasonWindow()

    }

    fun updateData(){
        modelObject.update()
    }

}