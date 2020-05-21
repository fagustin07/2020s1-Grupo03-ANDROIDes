package org.unqflix.exception

class InvalidFieldException(message: String) : RuntimeException(message)

class NotFoundUser: Exception("User not found")

class NotFoundToken : Exception("Token not found")
