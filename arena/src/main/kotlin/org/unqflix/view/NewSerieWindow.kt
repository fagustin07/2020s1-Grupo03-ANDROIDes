package org.unqflix.view

import ICON
import domain.ExistsException
import org.unqflix.exceptions.EmptyFieldException
import org.unqflix.model.NewSerieAppModel
import org.unqflix.model.CategoryAppModel
import org.unqflix.model.SerieAppModel
import org.uqbar.commons.model.exceptions.UserException
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.widgets.List
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner

class NewSerieWindow(owner: WindowOwner, model: NewSerieAppModel) : SimpleWindow<NewSerieAppModel>(owner, model) {

    override fun createFormPanel(mainPanel: Panel?) {
        title= "Creating new Serie"
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
                setHeight(100)
                bindItemsTo("chosenCategories").adaptWithProp<CategoryAppModel>("name")
                bindSelectedTo("categoryToRemove")
            }

            List<CategoryAppModel>(it) with {
                setHeight(100)
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
                    setWidth(100)
                    bindItemsTo("relatedContent").adaptWithProp<SerieAppModel>("title")
                    bindSelectedTo("relatedSerieToRemove")
                }
                List<SerieAppModel>(it) with {
                    setWidth(100)
                    bindItemsTo("allSeries").adaptWithProp<SerieAppModel>("title")
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

    override fun addActions(p0: Panel?) {
        Button(p0) with {
            caption= "Create"
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
        Button(p0) with {
            caption= "Cancel"
            onClick { close() }
        }

    }
    private fun tryAddToSystem(){
        try {
            modelObject.addSerieToSystem()
        }catch (e:ExistsException){
            throw UserException(e.message)
        }
    }
    private fun checkFields() {
        if (modelObject.title=="" || modelObject.chosenCategories.isEmpty()){
            throw EmptyFieldException("TITLE AND CATEGORY CANT BE ARE EMPTY")
        }
    }


}
