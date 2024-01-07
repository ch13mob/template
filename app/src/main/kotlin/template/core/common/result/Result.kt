package template.core.common.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import template.core.common.error.Error
import template.core.common.error.toError

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val error: Error) : Result<Nothing>
}

fun <T> Result<T>.fold(onSuccess: (T) -> Unit, onFailure: (Error) -> Unit) {
    when (this) {
        is Result.Success -> onSuccess(data)
        is Result.Failure -> onFailure(error)
    }
}

val <T> Result<T>.isSuccess: Boolean
    get() = this is Result.Success

val <T> Result<T>.isFailure: Boolean
    get() = this is Result.Failure

fun <T> Result<T>.onSuccess(onSuccess: (T) -> Unit): Result<T> {
    fold(onSuccess) {}
    return this
}

fun <T> Result<T>.onFailure(onFailure: (Error) -> Unit): Result<T> {
    fold({}, onFailure)
    return this
}

fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> {
            Result.Success(it)
        }
        .catch { emit(Result.Failure(it.toError)) }
}
