package org.unqflix.view

import ICON
import domain.Content
import domain.ExistsException
import domain.Serie
import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.model.CategoryAppModel
import org.unqflix.model.EditSerieAppModel
import org.unqflix.model.NewSerieAppModel
import org.unqflix.model.SerieAppModel
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException

class EditSerieWindow(owner: WindowOwner, model: EditSerieAppModel) : SimpleWindow<EditSerieAppModel>(owner, model) {

    override fun createFormPanel(mainPanel: Panel?) {
        title= "Modify Serie"
        iconImage= ICON

        baseInformation(mainPanel)
        categoriesPanel(mainPanel)
        relatedContentPanel(mainPanel)

    }


    private fun baseInformation(mainPanel: Panel?) {
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


    private fun categoriesPanel(mainPanel: Panel?) {

        GroupPanel(mainPanel) with {
            title="Category"
            asColumns(2)
            List<CategoryAppModel>(it) with {
                setHeight(120)
                bindItemsTo("serieCategories").adaptWithProp<CategoryAppModel>("name")
                bindSelectedTo("categoryToRemove")
            }

            List<CategoryAppModel>(it) with {
                setHeight(120)
                bindItemsTo("categories").adaptWithProp<CategoryAppModel>("name")
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

    private fun relatedContentPanel(mainPanel: Panel?) {
        GroupPanel(mainPanel) with {
            title = "Related content: "
            asColumns(2)
            List<SerieAppModel>(it) with {
                setWidth(120)
                bindItemsTo("relatedContent").adaptWithProp<SerieAppModel>("title")
                bindSelectedTo("contentToRemove")
            }
            List<SerieAppModel>(it) with {
                setWidth(120)
                bindItemsTo("series").adaptWithProp<SerieAppModel>("title")
                bindSelectedTo("contentSelected")
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

    override fun addActions(mainPanel: Panel) {
        Button(mainPanel) with {
            caption= "Modify"
            onClick {
                try {
                    checkFields()
                    tryAddToSystem()
                    close()
                }catch(e: EmptyFieldException){
                    throw UserException(e.message)
                }
            }
        }
        Button(mainPanel) with {
            caption= "Cancel"
            onClick { close() }
        }

    }
    private fun tryAddToSystem(){
        try {
            modelObject.modifySerie()
        }catch (e: ExistsException){
            throw UserException(e.message)
        }
    }
    private fun checkFields() {
        if (modelObject.title=="" || modelObject.serieCategories?.isEmpty()!!){
            throw EmptyFieldException("TITLE AND CATEGORY CANT BE ARE EMPTY")
        }
    }

}