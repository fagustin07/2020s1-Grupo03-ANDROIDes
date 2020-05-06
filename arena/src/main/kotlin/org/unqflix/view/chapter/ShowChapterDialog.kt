package org.unqflix.view.chapter

import ICON
import org.unqflix.model.ChapterAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class ShowChapterDialog(owner : WindowOwner, model : ChapterAppModel?) : Dialog<ChapterAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title = "Showing Chapter"
        iconImage = ICON
        GroupPanel(mainPanel) with { groupPanel ->
            title= "Chapter Information"
            Label(groupPanel) withText ("Title: ${thisWindow.modelObject.title}")
            Label(groupPanel) withText ("Duration: ${thisWindow.modelObject.minutes()}")
            Panel(groupPanel) with {
                asHorizontal()
                Label(it) withText ("Video:")
                Link(it) with {
                    text = thisWindow.modelObject.video
                }
            }
        }
    }


    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "Back"
            onClick { thisWindow.close() }
        }
    }
}