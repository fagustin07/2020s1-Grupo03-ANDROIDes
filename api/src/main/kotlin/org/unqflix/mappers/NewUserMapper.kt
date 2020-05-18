package org.unqflix.mappers

class NewUserMapper(val name:String?, val password:String?, val creditCard:String?, val image:String?,val email:String?)
{
    fun checkFields()= name!=null && password != null && creditCard!=null && image!=null && email!=null
}

