package org.unqflix.model

import domain.*
import org.uqbar.commons.model.annotations.Observable

@Observable
class NewSerieAppModel(model: UNQFlix, var idGenerator: IdGenerator) {

    val system = model
    var title = ""
        set(value){
            field=value.toLowerCase()
        }
    var poster = ""
    var description=""
    var status = false
    var serieSelected : SerieAppModel? = null
    var relatedSerieToRemove: SerieAppModel? = null
    var categorySelected : CategoryAppModel? = null
    var categoryToRemove: CategoryAppModel? = null
    var allSeries : MutableList<SerieAppModel>
    var categories : MutableList<CategoryAppModel>
    var relatedContent = mutableListOf<SerieAppModel>()
    var chosenCategories = mutableListOf<CategoryAppModel>()

    init{
        categories = categoriesToAppModel()

        val series = mutableListOf<SerieAppModel>()
        system.series.forEach { series.add(SerieAppModel(it)) }
        allSeries= series
    }

    private fun categoriesToAppModel(): MutableList<CategoryAppModel> {

        val categoriesList = mutableListOf<CategoryAppModel>()
        system.categories.forEach { categoriesList.add(CategoryAppModel(it)) }

        return categoriesList
    }

     fun addCategory(){
        if(!chosenCategories.contains(categorySelected)) {
            categorySelected?.let { chosenCategories.add(it) }
        }
    }
     fun removeCategory(){
        chosenCategories.remove(categoryToRemove)
    }
     fun addRelatedContent(){
        if(!relatedContent.contains(serieSelected)) {
            serieSelected?.let { relatedContent.add(it) }
        }
    }
     fun removeRelatedContent(){
        relatedContent.remove(relatedSerieToRemove)
    }

    fun addSerieToSystem(){

        var newSerie = Serie(idGenerator.nextSerieId(),title,description,poster,serieState(), selectedCategories(),
        relatedContent = relatedContent())
        system.addSerie(newSerie)
    }

    private fun relatedContent(): MutableList<Content> {
        var contentList = mutableListOf<Content>()

        contentList.addAll(relatedContent.map { it.serie }.toMutableList())
        return contentList
    }
    private fun serieState()= if (status) Available() else Unavailable()

    private fun selectedCategories()= chosenCategories.map { it.category }.toMutableList()
}