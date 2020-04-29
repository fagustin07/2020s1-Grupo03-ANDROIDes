package org.unqflix.view.serie

import ICON
import data.idGenerator
import domain.Season
import org.unqflix.model.*
import org.unqflix.view.season.EditSeasonDialog
import org.unqflix.view.season.NewSeasonDialog
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.lacar.ui.model.Action
import java.awt.Color

class ShowSerieDialog(owner : WindowOwner, model : SerieAppModel) : Dialog<SerieAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title= "Seasons from ${modelObject.title}"
        iconImage= ICON
        Label(mainPanel) with {
            fontSize=11
            bgColor= Color(0,164,144)
            color= Color(250,250,200)
            text = "~ ${thisWindow.modelObject.idAndTitle} ~" }

        makeTableOfSeasons(mainPanel)
    }

    fun makeTableOfSeasons(mainPanel : Panel){
        table<SeasonAppModel>(mainPanel) with {
            visibleRows = 7
            bindItemsTo("seasonsF")
            bindSelectionTo("seasonSelected")
            column {
                title = "#"
                fixedSize=50
                alignCenter()
                bindContentsTo("id")
            }
            column {
                title = "Season"
                fixedSize=120
                alignCenter()
                bindContentsTo("title")
            }
            column {
                title = "#Chapters"
                alignCenter()
                fixedSize=70
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
                    val newSeason = newSeason()
                    NewSeasonDialog(owner, SeasonAppModel(newSeason)) with {
                        onAccept {
                            addSeasonToSystem(newSeason)
                        }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Modify Season"
                onClick {
                    EditSeasonDialog(
                        owner, thisWindow.modelObject.seasonSelected?.model?.let
                        { season -> SeasonAppModel(season) }) with {
                        onAccept {updateData() }
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
                onClick { close() ; cancel()}
            }

        }
    }

        fun reopenSeasonWindow(){
            ShowSerieDialog(owner, modelObject).open()
        }


        fun newSeason() = Season(idGenerator.nextSeasonId(),"","","")

    fun addSeasonToSystem(season: Season){
        modelObject.addSeasonToSystem(SeasonAppModel(season))
    }

    fun updateData(){
        modelObject.update()
    }

}