package org.unqflix.mappers

import domain.Available
import domain.Content
import domain.ContentState
import domain.User

data class ViewUserMapper(val user: User) {

    fun simpleView() =
        mapOf("id" to "${user.id}", "name" to "${user.name}", "email" to "${user.email}", "image" to "${user.image}")

    fun complexView() = mapOf(
        "name" to "${user.name}", "image" to "${user.image}",
        "favorites" to "${contentToListMap(user.favorites)}", "lastSeen" to "${contentToListMap(user.lastSeen)}"
    )

    private fun contentToListMap(anContentList: MutableCollection<Content>)=
        anContentList.map{ mapOf("id" to "${it.id}", "title" to "${it.title}",
                "description" to "${it.description}", "state" to "${setContent(it.state)}")}.toList()

    private fun setContent(state: ContentState)= if(state::class== Available::class) "Available" else "Unavailable"
}
