package org.unqflix.view.season

import ICON
import domain.ExistsException
import domain.Season
import org.unqflix.exceptions.NoSelectItemException
import org.unqflix.model.*
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import java.awt.Color

class ShowSerieDialog(owner : WindowOwner, model : SerieAppModel?) : Dialog<SerieAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title = "Seasons from ${modelObject.title}"
        iconImage = ICON
            Label(mainPanel) with {
                fontSize = 11
                bgColor = Color(0, 164, 144)
                color = Color(250, 250, 200)
                text = "~ ${thisWindow.modelObject.idAndTitle()} ~"
            }
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
                    try {
                        checkSelectSeasonOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    EditSeasonDialog(
                        owner, thisWindow.modelObject.seasonSelected?.model?.let
                        { season -> SeasonAppModel(season, thisWindow.modelObject.seasons()) }) with {
                        onAccept {
                            updateSeasonsList()
                        }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Show chapters"
                onClick { }
            }
            Button(it) with {
                caption = "Delete Season"
                onClick{
                    try {
                        checkSelectSeasonOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    DeleteSeasonDialog(owner, thisWindow.modelObject.seasonSelected) with {
                        onAccept { deleteSeason() }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Back"
                onClick {
                    close()
                    cancel()
                }
            }

        }
    }

    private fun checkSelectSeasonOrException() {
        if (modelObject.seasonSelected == null) {
            throw NoSelectItemException("To do this, first, click on a season please.")
        }

    }
    fun deleteSeason(){
        modelObject.deleteSeason()
    }

    fun newSeason() = Season(IdGeneratorFactory.takeIdGen().nextSeasonId(),"","","")

    fun addSeasonToSystem(season: Season){
        try {
            modelObject.addSeasonToSystem(season)
            } catch (e: ExistsException){
              throw UserException(e.message)
        }
    }

    fun updateSeasonsList(){
        modelObject.updateSeasonsList()
    }

}