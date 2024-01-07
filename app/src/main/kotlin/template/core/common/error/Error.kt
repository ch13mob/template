package template.core.common.error

import java.lang.IllegalArgumentException
import java.net.UnknownHostException

class InvalidEmailException : IllegalArgumentException()
class InvalidPasswordException : IllegalArgumentException()

sealed class Error {
    data class General(val exception: Throwable) : Error()
    data object NoNetworkConnection : Error()

    data object InvalidCredentials {
        data object BadEmail : Error()
        data object BadPassword : Error()
    }
}

val Throwable.toError: Error
    get() = when (this) {
        is InvalidEmailException -> Error.InvalidCredentials.BadEmail
        is InvalidPasswordException -> Error.InvalidCredentials.BadPassword
        is UnknownHostException -> Error.NoNetworkConnection
        else -> Error.General(this)
    }
