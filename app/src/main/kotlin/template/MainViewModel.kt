package template

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import template.core.common.error.Error
import template.core.data.repository.UserDataRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _deeplink = MutableStateFlow<Uri?>(null)
    val deeplink: StateFlow<Uri?> = _deeplink.asStateFlow()

    val userAuth: StateFlow<UserAuthState> = userDataRepository.userData
        .map { userData ->
            UserAuthState(
                isLoading = false,
                isLoggedIn = userData.isLoggedIn
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserAuthState(isLoading = true)
        )

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.HandleDeeplink -> handleDeeplink(event.deeplink)
            is MainUiEvent.DeeplinkConsumed -> onDeeplinkConsumed()
            is MainUiEvent.HandleError -> handleError(event.error)
            is MainUiEvent.ErrorConsumed -> onErrorConsumed()
        }
    }

    private fun handleDeeplink(deeplink: Uri) {
        _deeplink.update { deeplink }
    }

    private fun onDeeplinkConsumed() {
        _deeplink.update { null }
    }

    private fun handleError(error: Error) {
        _uiState.update { it.copy(error = error) }
    }

    private fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}

sealed interface MainUiEvent {
    data class HandleDeeplink(val deeplink: Uri) : MainUiEvent
    data object DeeplinkConsumed : MainUiEvent
    data class HandleError(val error: Error) : MainUiEvent
    data object ErrorConsumed : MainUiEvent
}

data class MainUiState(
    val error: Error? = null
)

data class UserAuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = true
)
