package org.unqflix.view

import ICON
import domain.ExistsException
import domain.Serie
import domain.Unavailable
import org.unqflix.exceptions.NoSelectSerieException
import org.unqflix.model.*
import org.unqflix.view.serie.EditSerieDialog
import org.unqflix.view.serie.NewSerieDialog
import org.unqflix.view.serie.RemoveSerieDialog
import org.unqflix.view.season.ShowSerieDialog
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
                    close()
                    val newSerie=newSerie()
                    NewSerieDialog(
                        owner,
                        SerieAppModel(newSerie, thisWindow.modelObject.categories(), thisWindow.modelObject.series())
                    ) with {
                        onAccept{
                            addSerieToSystem(newSerie)
                            reopenPrincipalWindow()
                        }
                        onCancel{
                            reopenPrincipalWindow()
                        }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Modify selected"
                onClick {
                    try {
                        checkSelectSerieOrException()
                    } catch (e: NoSelectSerieException) {
                        throw UserException(e.message)
                    }
                    EditSerieDialog(owner,
                        thisWindow.modelObject.selectedSerie?.serie?.let
                        { serie -> SerieAppModel(serie, categories(), series()) }) with {
                        onAccept{
                            restartFilter()
                            reopenPrincipalWindow()
                        }
                        onCancel{
                            reopenPrincipalWindow()
                        }
                        open()
                    }
                }
            }
            Button(it) with {
                caption = "Show selected"
                onClick {
                    try {
                        checkSelectSerieOrException()
                    } catch (e: NoSelectSerieException) {
                        throw UserException(e.message)
                    }
                    thisWindow.modelObject.selectedSerie?.let { serie ->
                        ShowSerieDialog(thisWindow.owner, serie).open()
                    }

                }
            }
            Button(it) with {
                caption = "Delete selected"
                onClick {
                    try {
                        checkNoSelectedException()
                    } catch (e: NoSelectSerieException) {
                        throw UserException(e.message)
                    }
                    RemoveSerieDialog(thisWindow, thisWindow.modelObject) with {
                        onAccept { thisWindow.modelObject.removeSerie() }
                        onCancel { close() }
                        open()
                       restartFilter()
                    }
                }
            }
        }
    }

    private fun reopenPrincipalWindow() {
        UNQflixWindow(owner,modelObject).open()
    }
    private fun restartFilter() {
        modelObject.restartFilter()
    }


    private fun newSerie(): Serie {
        return Serie(modelObject.idGenerator.nextSerieId(),"","","", Unavailable())
    }

    private fun addSerieToSystem(newSerie: Serie) {
        try {
            modelObject.addSerie(newSerie)
        }catch(e: ExistsException){
            throw UserException(e.message)
        }
        reopenPrincipalWindow()
    }

    private fun categories()=modelObject.categories()
    private fun series()=modelObject.series()

    private fun checkSelectSerieOrException() {
        checkNoSelectedException()
    }

    private fun checkNoSelectedException() {
        if (modelObject.selectedSerie == null) {
            throw NoSelectSerieException("To do this, first, click on a serie please.")
        }
    }

    private fun makeSearchBox(mainPanel: Panel) {
        Panel(mainPanel) with {
            asHorizontal()
            Label(it) with{
                text= "Search: "
                fontSize=10
            }
            TextBox(it) with {
                width = 270
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
               title = "#"
                fixedSize=50
              alignCenter()
                bindContentsTo("id")
            }
            column {
                title = "Serie"
                fixedSize=150
                alignCenter()
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
                fixedSize=50
                alignCenter()
                bindContentsTo("state")
            }
        }

    }
}
