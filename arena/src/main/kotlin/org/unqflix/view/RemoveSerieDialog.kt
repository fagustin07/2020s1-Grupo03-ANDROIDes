package org.unqflix.view

import ICON
import org.unqflix.model.UNQflixAppModel
import org.uqbar.arena.kotlin.extensions.asHorizontal
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.kotlin.extensions.withText
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner

class RemoveSerieDialog(owner: WindowOwner, model: UNQflixAppModel) : Dialog<UNQflixAppModel>(owner,model) {
    override fun createFormPanel(p0: Panel?) {
        title = "Removing Serie"
        iconImage = ICON
        Label(p0) withText ("Hey wait. Are you sure to remove '${modelObject.selectedSerie?.title}' \n" +
                " of the system? This can't undone!")

        Panel(p0) with {
            asHorizontal()
            Button(it) with {

                caption = "Confirm"
                onClick { accept() }
            }
            Button(it) with {
                caption = "Cancel"
                onClick { cancel() }
            }
        }

    }
}
