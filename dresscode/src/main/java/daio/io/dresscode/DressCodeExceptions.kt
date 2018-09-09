package daio.io.dresscode

import kotlin.RuntimeException

class DressCodeNotRegisteredException(message: String) : RuntimeException(message)
class DressCodeAlreadyInitialisedException(message: String) : RuntimeException(message)
