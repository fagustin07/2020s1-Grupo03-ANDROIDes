package org.unqflix.view


import org.unqflix.model.UNQflixAppModel
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window


fun main(){
    ApplicationUNQflix().start()
}

class ApplicationUNQflix: Application() {

    override fun createMainWindow(): Window<*> {
        return UNQflixWindow(this, UNQflixAppModel())
    }

}
