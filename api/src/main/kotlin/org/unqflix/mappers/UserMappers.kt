package org.unqflix.mappers

data class  LogInDataMapper(var email: String ="", var password:String="")

class NewUserMapper(val name:String?, val password:String?,val repeatedPass:String?,
                    val creditCard:String?, val image:String?,val email:String?) {
    fun checkFields()= name!=null && password != null && repeatedPass != null && creditCard!=null && image!=null && email!=null
}

data class ViewUserMapper(val id:String, val name:String,val email:String,val image:String)

data class ViewUserDataMapper(val name:String, val image:String,
                              val favorites:List<ContentMapper>, val lastSeen:MutableList<ContentMapper>)
