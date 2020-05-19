package org.unqflix.mappers

class NewUserMapper(val name:String?, val password:String?, val creditCard:String?, val image:String?,val email:String?)
{
    fun checkFields()= name!=null && password != null && creditCard!=null && image!=null && email!=null
}

data class ViewUserMapper(val id:String, val name:String,val email:String,val image:String)

data class ViewUserDataMapper(val name:String,val image:String,
                              val favorites:MutableList<ContentSimpleMapper>, val lastSeen:MutableList<ContentSimpleMapper>)
