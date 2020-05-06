package org.unqflix.view.chapter

import ICON
import org.unqflix.model.ChapterAppModel
import org.uqbar.arena.kotlin.extensions.asHorizontal
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.kotlin.extensions.withText
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class DeleteChapterDialog(owner : WindowOwner, model : ChapterAppModel?) : Dialog<ChapterAppModel>(owner,model) {
    override fun createFormPanel(mainPanel: Panel?) {
        title = "Chapter to remove"
        iconImage = ICON
        Label(mainPanel) withText ("Are you sure to remove the chapter '${modelObject?.title}'\n" +
                " from the season '${modelObject.seasonWhoBelongs.title}'? This can't undone!")
        Panel(mainPanel) with {
            asHorizontal()
            Button(it) with {
                caption = "Confirm"
                onClick { accept() }
            }
            Button(it) with {
                caption = "Cancel"
                onClick { close() }
            }
        }
    }
}