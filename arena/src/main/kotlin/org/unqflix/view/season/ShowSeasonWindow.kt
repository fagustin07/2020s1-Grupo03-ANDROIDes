package org.unqflix.view.season

import ICON
import domain.Chapter
import org.unqflix.exceptions.NoSelectItemException
import org.unqflix.model.ChapterAppModel
import org.unqflix.model.IdGeneratorFactory
import org.unqflix.model.SeasonAppModel
import org.unqflix.view.chapter.DeleteChapterDialog
import org.unqflix.view.chapter.EditChapterDialog
import org.unqflix.view.chapter.NewChapterDialog
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import java.awt.Color

class ShowSeasonWindow (owner : WindowOwner, model : SeasonAppModel?) : SimpleWindow<SeasonAppModel>(owner,model) {

    override fun createFormPanel(p0: Panel) {
        title =  "Showing season"
        iconImage = ICON
            Label(p0) with {
                fontSize = 11
                bgColor = Color(0, 164, 144)
                color = Color(250, 250, 200)
                text = "~ ${thisWindow.modelObject.title} ~"
            }
            makeTableOfChapter(p0)
        }

    fun makeTableOfChapter(p0 : Panel){
        table<ChapterAppModel>(p0) with {
            visibleRows = 7
            bindItemsTo("seasonChapters")
            bindSelectionTo("chapterSelected")
            column {
                title = "Chapter"
                fixedSize=150
                bindContentsTo("title")
            }
            column {
                title = "Minutes"
                alignCenter()
                fixedSize=70
                bindContentsTo("duration")
            }
        }

    }

    override fun addActions(p0: Panel) {
        Panel(p0) with {
            asColumns(2)
            Button(it) with {
                caption = "Add new chapter"
                onClick {
                    val newChapter = newChapter()
                    NewChapterDialog(
                            owner, ChapterAppModel(newChapter, thisWindow.modelObject.model)).open()
                    updateChapterList()
                }
            }
            Button(it) with {
                caption = "Modify chapter"
                onClick {
                    try {
                        checkSelectChapterOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    EditChapterDialog(
                            owner, thisWindow.modelObject.chapterSelected?.model?.let
                            { chapter -> ChapterAppModel(chapter, thisWindow.modelObject.model) }).open()
                    updateChapterList()
                }
            }
            Button(it) with {
                caption = "Show chapter"

                onClick {/*
                    try {
                        checkSelectSeasonOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    ShowChapterDialog (owner,
                            thisWindow.modelObject.seasonSelected?.model?.let { model -> SeasonAppModel(model,model.serieWhoBelongs) }).open()
                */}
            }
            Button(it) with {
                caption = "Delete chapter"
                onClick{
                    try {
                        checkSelectChapterOrException()
                    } catch (e: NoSelectItemException) {
                        throw UserException(e.message)
                    }
                    DeleteChapterDialog(
                            owner,
                            thisWindow.modelObject.chapterSelected
                    ) with {
                        onAccept { thisWindow.modelObject.removeChapterFromSystem() }
                        open()
                    }
                    updateChapterList()
                }
            }
            Button(it) with {
                caption = "Close"
                onClick {
                    close()
                }
            }

        }
    }

    private fun checkSelectChapterOrException() {
        if (modelObject.chapterSelected == null) {
            throw NoSelectItemException("To do this, first, click on a chapter please.")
        }

    }

    fun newChapter() = Chapter(IdGeneratorFactory.takeIdGen().nextSeasonId(),"","",1,"","")


    fun updateChapterList(){
        modelObject.updateChapterList()
    }

}