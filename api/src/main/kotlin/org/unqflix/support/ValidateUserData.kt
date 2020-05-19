package org.unqflix.support

import org.unqflix.exception.InvalidFieldException
import org.unqflix.mappers.NewUserMapper

class ValidateUserData {

    fun validate(data: NewUserMapper){
        validateName(data.name!!)
        validateEmail(data.email!!)
        validatePassword(data.password!!)
        validateCreditCard(data.creditCard!!)
        validateImage(data.image!!)

    }

    private fun validateName(name: String) {
        val regex = Regex("^\\S+(\\s+\\S+)['`\\-]*$")
        if (!name.matches(regex))
            throw InvalidFieldException("Invalid name: only letters are allowed. Please, submit your name and surname.")
    }

    private fun validateEmail(email: String) {
        val regex= Regex("^[^@]+@[^@]+\\.[a-zA-Z]{2,}\$")
        if(!email.matches(regex))
            throw InvalidFieldException("Invalid email: Please, check that and try again.")
    }

    private fun validatePassword(password: String) {
        val regex = Regex("^(?=.*\\d)(?=.*[A-Z])[\\w!@#$%^&*()_+=\\-{}\\[\\];'|?><,./:\\s]{6,16}\$")
        if(!password.matches(regex))
            throw InvalidFieldException("Invalid password: For security reasons, your password must be between 6 and 16 characters, " +
                        "and contains at least one capital letter and one number.")
    }

    private fun validateCreditCard(creditCard: String) {
        val regex = Regex("^([\\d{4}\\s]){19}$")
        if(!creditCard.matches(regex))
            throw InvalidFieldException("Invalid credit card: Please, insert your credit card number with format" +
                        " 'XXXX XXXX XXXX XXXX'.")
    }

    private fun validateImage(image: String) {
        val regex= Regex("^(https?:\\/\\/)?([\\w\\-])+\\.([a-zA-Z]{2,63})([\\/\\w-]*)*\\/?\\??([^#\\n\\r]*)?#?([^\\n\\r]*)")
        if(!image.matches(regex))
            throw InvalidFieldException("Invalid image: the URL image submitted was invalid. Please, try again.")
    }
}