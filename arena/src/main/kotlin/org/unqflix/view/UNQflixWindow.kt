package org.unqflix.view

import ICON
import domain.Serie
import domain.Unavailable
import org.unqflix.exceptions.NoAvailableException
import org.unqflix.exceptions.NoSelectItemException
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
                    val newSerie=newSerie()
                    close()
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
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    close()
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
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    try {
                        checkIsNotAvailable()
                    } catch (e : NoAvailableException) {
                        throw UserException(e.message)
                    }
                    close()
                    ShowSerieDialog(owner,
                        thisWindow.modelObject.selectedSerie?.serie?.let
                        { serie -> SerieAppModel(serie) }) with {
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
                caption = "Delete selected"
                onClick {
                    try {
                        checkSelectSerieOrException()
                    } catch (e: NoSelectItemException) {
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
        return Serie(IdGeneratorFactory.takeIdGen().nextSerieId(),"","","", Unavailable())
    }

    private fun addSerieToSystem(newSerie: Serie) {
        modelObject.addSerie(newSerie)
    }

    private fun categories()=modelObject.categories()
    private fun series()=modelObject.series()

    private fun checkSelectSerieOrException() {
        modelObject.checkNoSelectedException()
    }

    private fun checkIsNotAvailable(){
        modelObject.checkIsNotAvailable()
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
