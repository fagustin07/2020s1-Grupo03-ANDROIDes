package org.unqflix.model

import domain.Category
import org.uqbar.commons.model.annotations.Observable

@Observable
class CategoryAppModel(var category:Category) {

    var name = category.name
}
