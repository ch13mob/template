package template.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import template.core.common.error.Error
import template.core.common.result.fold
import template.core.domain.SignInParams
import template.core.domain.SignInUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> onEmailChanged(event.email)
            is LoginUiEvent.PasswordChanged -> onPasswordChanged(event.password)
            is LoginUiEvent.Login -> login(event.email, event.password)
            is LoginUiEvent.ErrorConsumed -> onErrorConsumed()
        }
    }

    private fun onEmailChanged(email: String) {
        this.email = email
        _uiState.update {
            it.copy(
                isEmailValid = true,
                isLoginButtonEnabled = isLoginButtonEnabled()
            )
        }
    }

    private fun onPasswordChanged(password: String) {
        this.password = password
        _uiState.update {
            it.copy(
                isPasswordValid = true,
                isLoginButtonEnabled = isLoginButtonEnabled()
            )
        }
    }

    private fun isLoginButtonEnabled() =
        email.isNotEmpty() && password.isNotEmpty()

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            val signInParams = SignInParams(email, password)

            signInUseCase(signInParams)
                .onEach { result ->
                    result.fold(
                        onSuccess = {
                            _uiState.update { it.copy(isLoading = false) }
                        },
                        onFailure = ::handleFailure
                    )
                }
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun handleFailure(error: Error) {
        _uiState.update { it.copy(isLoading = false) }

        when (error) {
            is Error.InvalidCredentials.BadEmail -> {
                _uiState.update { it.copy(isEmailValid = false) }
            }

            is Error.InvalidCredentials.BadPassword -> {
                _uiState.update { it.copy(isPasswordValid = false) }
            }

            else -> _uiState.update { it.copy(error = error) }
        }
    }

    private fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}

sealed interface LoginUiEvent {
    data class EmailChanged(val email: String) : LoginUiEvent
    data class PasswordChanged(val password: String) : LoginUiEvent
    data class Login(val email: String, val password: String) : LoginUiEvent
    data object ErrorConsumed : LoginUiEvent
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoginButtonEnabled: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val error: Error? = null
)
