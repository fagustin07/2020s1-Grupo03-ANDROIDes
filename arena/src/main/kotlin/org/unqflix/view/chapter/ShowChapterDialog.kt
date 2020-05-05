package org.unqflix.view.chapter

import ICON
import org.unqflix.model.ChapterAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Link
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class ShowChapterDialog(owner : WindowOwner, model : ChapterAppModel?) : Dialog<ChapterAppModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        title = "View Chapter"
        iconImage = ICON
        Label(mainPanel) withText ("Aqui esta tu video:")
        Link(mainPanel) with {
            text = modelObject.video
        }

}
    override fun addActions(actionsPanel: Panel) {
        Button(actionsPanel) with {
            caption = "Back"
            onClick { thisWindow.close() }
        }
    }
}