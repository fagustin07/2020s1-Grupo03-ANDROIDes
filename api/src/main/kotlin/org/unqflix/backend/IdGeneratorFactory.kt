package org.unqflix.backend

import domain.IdGenerator

object IdGeneratorFactory {
    val idGenerator = IdGenerator()

    fun takeIdGen()= idGenerator
}