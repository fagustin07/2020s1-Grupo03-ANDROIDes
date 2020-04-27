package org.unqflix.model

import domain.*
import org.uqbar.commons.model.annotations.Observable

@Observable
class EditSerieAppModel(selectedSerie : SerieAppModel?, model : UNQFlix) {
    val system = model
    val serieToEdit = selectedSerie
    var title = selectedSerie?.title

    var poster = selectedSerie?.model?.poster
    var description= selectedSerie?.model?.description
    var status = stateActual()
    var categorySelected : CategoryAppModel? = null
    var categoryToRemove: CategoryAppModel? = null
    var serieCategories : MutableList<CategoryAppModel>?
    var categories : MutableList<CategoryAppModel>
    var series : MutableList<SerieAppModel>
    var contentSelected : SerieAppModel? = null
    var contentToRemove : SerieAppModel? = null
    var relatedContent  : MutableList<SerieAppModel> = mutableListOf()


    init{
        categories = categoryToAppModel(system.categories)
        serieCategories = categoryToAppModel(serieToEdit?.serie?.categories)

       // val contents = mutableListOf<SerieAppModel>()
        //serieToEdit?.serie?.relatedContent?.forEach { contents.add(it)}
        //contents.forEach {relatedContent.add(it)}

        val series = mutableListOf<SerieAppModel>()
        system.series.forEach { series.add(SerieAppModel(it)) }
        this.series = series


    }

    private fun <E> MutableList<E>.add(element: Content) {
    }
    private fun categoryToAppModel(categories : MutableList<Category>?) : MutableList<CategoryAppModel> {
        var categoriesList = mutableListOf<CategoryAppModel>()

        if (categories != null) {
            categories.forEach{categoriesList.add(CategoryAppModel(it))}
        }

        return categoriesList
    }

    private  fun stateActual() = (serieToEdit?.model?.state!!::class == Available()::class)

     fun addCategory(){
        if(!serieCategories?.contains(categorySelected)!!){
            categorySelected?.let { serieCategories?.add(it) }
        }

    }

     fun removeCategory(){
        serieCategories?.remove(categoryToRemove)
    }

    fun addRelatedContent(){
        if(!relatedContent.contains(contentSelected)!!){
            contentSelected?.let { relatedContent.add(it) }
        }
    }

    fun removeRelatedContent(){
        relatedContent?.remove(contentToRemove)

    }

     fun modifySerie(){
         val serieId = serieToEdit?.model?.id
        serieToEdit?.id?.let { system.deleteSerie(it) }

         var newSerie =
             serieId?.let { title?.let { it1 -> relatedContent()?.let { it2 ->
                 description?.let { it3 ->
                     poster?.let { it4 ->
                         selectedCategories()?.let { it5 ->
                             serieToEdit?.model?.seasons?.let { it6 ->
                                 Serie(it, it1, it3, it4,serieState(), it5, it6,
                                     it2
                                 ) } } } } } } }

         if (newSerie != null) {
             system.addSerie(newSerie)
         }


    }
    private fun relatedContent(): MutableList<Content> {
        val contentList = mutableListOf<Content>()

        contentList.addAll(relatedContent.map { it.serie }.toMutableList())

        return contentList
    }

    private fun serieState() = if (status) Available() else Unavailable()
    private  fun selectedCategories() = serieCategories?.map { it.category }?.toMutableList()






}







