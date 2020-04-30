package org.unqflix.model

import domain.IdGenerator

object IdGeneratorFactory {
    val idGenerator = IdGenerator()

    fun takeIdGen()= idGenerator
}