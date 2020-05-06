package org.unqflix.view.serie

import ICON
import domain.Season
import org.unqflix.exceptions.NoSelectItemException
import org.unqflix.model.*
import org.unqflix.view.season.DeleteSeasonDialog
import org.unqflix.view.season.EditSeasonWindow
import org.unqflix.view.season.NewSeasonWindow
import org.unqflix.view.season.ShowSeasonWindow
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import java.awt.Color

class ShowSerieWindow(owner : WindowOwner, model : SerieAppModel?) : SimpleWindow<SerieAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title =  "Showing serie"
        setMinHeight(400)
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
            bindItemsTo("serieSeasons")
            bindSelectionTo("seasonSelected")
            column {
                title = "Season"
                fixedSize=150
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
                    NewSeasonWindow(
                        owner,SeasonAppModel(newSeason, thisWindow.modelObject.serie)).open()
                    updateSeasonsList()
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
                    EditSeasonWindow(
                        owner, thisWindow.modelObject.seasonSelected?.model?.let
                        { season -> SeasonAppModel(season, thisWindow.modelObject.serie) }).open()
                    updateSeasonsList()
                }
            }
            Button(it) with {
                caption = "Show chapters"
                onClick {
                    try {
                        checkSelectSeasonOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    close()
                    ShowSeasonWindow(owner,
                            thisWindow.modelObject.seasonSelected?.let { model -> SeasonAppModel(model.model,model.serieWhoBelongs) }).open()
                    reopenWindow()
                }

            }
            Button(it) with {
                caption = "Delete Season"
                onClick{
                    try {
                        checkSelectSeasonOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    DeleteSeasonDialog(
                        owner,
                        thisWindow.modelObject.seasonSelected
                    ) with {
                        onAccept { thisWindow.modelObject.deleteFromSystem() }
                        open()
                    }
                    updateSeasonsList()
                }
            }
            Button(it) with {
                caption = "Back"
                onClick {
                    close()
                }
            }

        }
    }

    private fun reopenWindow() {
        ShowSerieWindow(owner,modelObject).open()
    }

    private fun checkSelectSeasonOrException() {
        if (modelObject.seasonSelected == null) {
            throw NoSelectItemException("To do this, first, click on a season please.")
        }

    }

    fun newSeason() = Season(IdGeneratorFactory.takeIdGen().nextSeasonId(),"","","")


    fun updateSeasonsList(){
        modelObject.updateSeasonsList()
    }

}