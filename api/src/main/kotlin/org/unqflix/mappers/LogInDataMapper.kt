package org.unqflix.mappers

import domain.User

class  LogInDataMapper(var email: String ="", var password:String="")
{
    fun match(anUser: User)= this.email.equals(anUser.email,ignoreCase = true) && this.password==anUser.password
}