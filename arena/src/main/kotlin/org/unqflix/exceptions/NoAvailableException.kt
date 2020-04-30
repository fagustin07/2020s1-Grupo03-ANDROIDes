package org.unqflix.exceptions

import java.lang.RuntimeException

class NoAvailableException(message : String) : RuntimeException(message) {
}