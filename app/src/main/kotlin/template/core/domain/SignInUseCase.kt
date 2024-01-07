package template.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import template.core.common.coroutines.AppDispatchers
import template.core.common.coroutines.Dispatcher
import template.core.common.error.InvalidEmailException
import template.core.common.error.InvalidPasswordException
import template.core.common.result.asResult
import template.core.data.repository.UserDataRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(params: SignInParams) = flow {
        params.validate()

        userDataRepository.login(params.email, params.password)
        emit(Unit)
    }
        .asResult()
        .flowOn(ioDispatcher)
}

data class SignInParams(
    val email: String,
    val password: String
) {
    fun validate() {
        if (email.length < CREDENTIALS_MIN_LENGTH) {
            throw InvalidEmailException()
        }

        if (password.length < CREDENTIALS_MIN_LENGTH) {
            throw InvalidPasswordException()
        }
    }

    companion object {
        private const val CREDENTIALS_MIN_LENGTH = 5
    }
}
