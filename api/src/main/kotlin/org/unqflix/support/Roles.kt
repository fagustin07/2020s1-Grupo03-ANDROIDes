package org.unqflix.support

import io.javalin.core.security.Role

enum class Roles : Role {
    ANYONE,USER,ADMIN
}