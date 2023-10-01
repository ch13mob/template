package com.ch13mob.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.template.core.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<LoginUiState> = _isLoading.map {
        LoginUiState(isLoading = it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LoginUiState(isLoading = false)
        )

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.Login -> login(event.email, event.password)
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.update { true }
            userDataRepository.login(email, password)
            _isLoading.update { false }
        }
    }
}

sealed class LoginUiEvent {
    data class Login(val email: String, val password: String) : LoginUiEvent()
}

data class LoginUiState(
    val isLoading: Boolean = false
)
