package org.unqflix.view.serie

import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.model.CategoryAppModel
import org.unqflix.model.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

abstract class ABMSerieWindow(owner: WindowOwner, model: SerieAppModel?) : SimpleWindow<SerieAppModel>(owner, model) {

    abstract override fun createFormPanel(mainPanel: Panel?)


    fun baseInformation(mainPanel: Panel?) {
        GroupPanel(mainPanel) with {
            title = "Serie info"
            asColumns(2)
            Label(it) withText ("Title: ")
            TextBox(it) with {
                width=150
                bindTo("title")
                withFilter { event -> event.potentialTextResult.matches(Regex("[/^a-zA-Z\\d\\-. ,!*?]*")) }
            }
            Label(it) withText ("Poster: ")
            TextBox(it) with {
                width=150
                bindTo("poster")
                withFilter { event -> event.potentialTextResult.matches(Regex("[/^a-zA-Z\\d\\-.,!*?]*")) }
            }
            Label(it) withText ("Description: ")
            KeyWordTextArea(it) with {
                width = 150
                height = 60
                bindTo("description")
            }
            Label(it) withText ("Is available: ")
            CheckBox(it) bindTo ("status")
        }
    }


    fun categoriesPanel(mainPanel: Panel?) {

        GroupPanel(mainPanel) with {
            title="Category"
            asColumns(2)
            List<CategoryAppModel>(it) with {
                setHeight(100)
                setWidth(70)
                bindItemsTo("chosenCategories").adaptWithProp<CategoryAppModel>("name")
                bindSelectedTo("categoryToRemove")
            }

            List<CategoryAppModel>(it) with {
                setHeight(100)
                setWidth(90)
                bindItemsTo("systemCategories").adaptWithProp<CategoryAppModel>("name")
                bindSelectedTo("categorySelected")

            }
            Button(it) with {
                caption = "remove"
                onClick { thisWindow.modelObject.removeCategory() }
            }
            Button(it) with {
                caption = "add"
                onClick { thisWindow.modelObject.addCategory() }
            }
        }
    }

    fun relatedContentPanel(mainPanel: Panel?) {
        GroupPanel(mainPanel) with {
            title = "Related content: "
            asColumns(2)
            List<SerieAppModel>(it) with {
                setWidth(150)
                setHeight(100)
                bindItemsTo("chosenSeries").adaptWithProp<SerieAppModel>("idAndTitle")
                bindSelectedTo("relatedSerieToRemove")
            }
            List<SerieAppModel>(it) with {
                setWidth(150)
                setHeight(100)
                bindItemsTo("allSeries").adaptWithProp<SerieAppModel>("idAndTitle")
                bindSelectedTo("serieSelected")
            }
            Button(it) with {
                caption = "remove"
                onClick { thisWindow.modelObject.removeRelatedContent() }
            }
            Button(it) with {
                caption = "add"
                onClick { thisWindow.modelObject.addRelatedContent() }
            }
        }
    }
    fun tryCheckTitle(){
        try {
            checkTitle()
        }catch(e: EmptyFieldException){
            throw UserException(e.message)
        }
    }
    private fun checkTitle() {
        if (modelObject.title=="" || modelObject.title.first()== ' '){
            throw EmptyFieldException("Field 'Title' cannot be empty or start with a space.\n Please, try again.")
        }
    }
}