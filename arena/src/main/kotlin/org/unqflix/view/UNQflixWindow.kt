package org.unqflix.view

import ICON
import org.unqflix.exceptions.NoSelectSerieException
import org.unqflix.model.*
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
                    NewSerieWindow(
                        owner, NewSerieAppModel(
                            thisWindow.modelObject.system,
                            thisWindow.modelObject.idGenerator
                        )
                    ).open()

                    thisWindow.modelObject.restartFilter()
                }
            }
            Button(it) with {
                caption = "Modify selected"
                onClick {
                    EditSerieWindow(
                        owner, EditSerieAppModel(
                                                thisWindow.modelObject.selectedSerie,thisWindow.modelObject.system)
                    ).open()

                    thisWindow.modelObject.restartFilter()
                }
            }
            Button(it) with {
                caption = "Show selected"
                onClick { thisWindow.close() ;
                    thisWindow.modelObject.selectedSerie?.let { it1 -> SeasonsView(owner, it1, thisWindow.modelObject).open() }

                }
            }
            Button(it) with {
                caption = "Delete selected"
                onClick {
                    try {
                        verifySelected()
                    } catch (e: NoSelectSerieException) {
                        throw UserException(e.message)
                    }
                    RemoveSerieDialog(thisWindow, thisWindow.modelObject) with {
                        onAccept { thisWindow.modelObject.removeSerie() }
                        onCancel { close() }
                        open()
                        thisWindow.modelObject.restartFilter()
                    }
                }
            }
        }
    }

    private fun verifySelected() {
        if (modelObject.selectedSerie==null){
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
                width = 200
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
                fixedSize=150
                alignCenter()
                bindContentsTo("title")
            }
            column {
                title = "Seasons"
                alignCenter()
                bindContentsTo("seasonsSize")
            }
            column {
                title = "State"
                alignCenter()
                bindContentsTo("state")
            }
        }

    }
}
