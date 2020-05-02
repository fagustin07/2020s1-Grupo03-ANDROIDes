package org.unqflix.view.season

import ICON
import org.unqflix.model.SeasonAppModel
import org.uqbar.arena.kotlin.extensions.asHorizontal
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.kotlin.extensions.withText
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class DeleteSeasonDialog(owner : WindowOwner, model : SeasonAppModel?) : Dialog<SeasonAppModel>(owner,model) {
    override fun createFormPanel(mainPanel: Panel?) {
        title = "Season to remove"
        iconImage = ICON
        Label(mainPanel) withText ("Are you sure to remove the season '${modelObject?.title}'\n of the system? This can't undone!")
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