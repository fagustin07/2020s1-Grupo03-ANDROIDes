package org.unqflix.view

import ICON
import domain.Serie
import domain.Unavailable
import org.unqflix.exceptions.NoSelectItemException
import org.unqflix.model.*
import org.unqflix.view.serie.EditSerieWindow
import org.unqflix.view.serie.NewSerieWindow
import org.unqflix.view.serie.DeleteSerieDialog
import org.unqflix.view.serie.ShowSerieWindow
import org.uqbar.commons.model.exceptions.UserException
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import java.awt.Color

class UNQflixWindow(owner: WindowOwner, unqflixAppModel: UNQflixAppModel):
                                SimpleWindow<UNQflixAppModel>(owner,unqflixAppModel) {


    override fun createFormPanel(mainPanel: Panel) {

        title = "UNQflix Administrator"
        iconImage= ICON
        setMinWidth(350)
        setMinHeight(400)
        Label(mainPanel) with{
            fontSize=14
            bgColor= Color(0,164,144)
            color= Color(250,250,200)
            text="Welcome!"
        }

        makeSearchBox(mainPanel)
        makeSeriesTable(mainPanel)
        Label(mainPanel) with{
            alignLeft()
            text="Here, you can management the series:"
            fontSize = 10
        }
    }

    override fun addActions(mainPanel: Panel?) {
        Panel(mainPanel) with {
            asColumns(2)
            Button(it) with {
                caption = "Add new serie"
                onClick {
                    val newSerie = newSerie()
                    NewSerieWindow(
                        owner,
                        SerieAppModel(newSerie, thisWindow.modelObject.categories(), thisWindow.modelObject.series())
                    ).open()
                    restartFilter()
                }

            }
            Button(it) with {
                caption = "Modify selected"
                onClick {
                    try {
                        checkSelectSerieOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    EditSerieWindow(owner,
                        thisWindow.modelObject.selectedSerie?.serie?.let
                        { serie -> SerieAppModel(serie, categories(), series()) }).open()
                    restartFilter()
                }
            }
            Button(it) with {
                caption = "Show selected"
                onClick {
                    try {
                        checkSelectSerieOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    ShowSerieWindow(owner,
                            thisWindow.modelObject.selectedSerie?.serie?.let { serie -> SerieAppModel(serie) }).open()
                    restartFilter()
                }

            }
            Button(it) with {
                caption = "Delete selected"
                onClick {
                    try {
                        checkSelectSerieOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    DeleteSerieDialog(thisWindow, thisWindow.modelObject.selectedSerie) with {
                        onAccept { modelObject.removeFromSystem() }
                        onCancel { close() }
                        open()
                       restartFilter()
                    }
                }
            }
        }
    }

    private fun restartFilter() {
        modelObject.restartFilter()
    }


    private fun newSerie(): Serie {
        return Serie(IdGeneratorFactory.takeIdGen().nextSerieId(),"","","", Unavailable())
    }

    private fun categories()=modelObject.categories()
    private fun series()=modelObject.series()

    private fun checkSelectSerieOrException() {
        modelObject.checkNoSelectedException()
    }

    private fun makeSearchBox(mainPanel: Panel) {
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with{
                text= "Search: "
                fontSize=10
            }
            TextBox(it) with {
                width = 250
                fontSize=10
                bindTo("serieToSearch")
            }
        }
    }


    private fun makeSeriesTable(mainPanel: Panel) {

        table<SerieAppModel>(mainPanel) with {
            visibleRows = 7
            bindItemsTo("filteredSeries")
            bindSelectionTo("selectedSerie")
            column {
                title = "Serie"
                fixedSize=180
                bindContentsTo("title")
            }
            column {
                title = "#Seasons"
                fixedSize=70
                alignCenter()
                bindContentsTo("seasonsSize")
            }
            column {
                title = "State"
                fixedSize=40
                alignCenter()
                bindContentsTo("state")
            }
        }

    }
}
