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
import template.core.data.repository.UserDataRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {

    private val _deeplink = MutableStateFlow<Uri?>(null)
    val deeplink: StateFlow<Uri?> = _deeplink.asStateFlow()

    val userAuth: StateFlow<UserAuthState> = userDataRepository.userData
        .map { userData ->
            if (userData.isLoggedIn) {
                UserAuthState.LoggedIn
            } else {
                UserAuthState.LoggedOut
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UserAuthState.Loading
        )

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.Deeplink -> handleDeeplink(event.deeplinkUri)
            is MainUiEvent.DeeplinkConsumed -> onDeeplinkConsumed()
        }
    }

    private fun handleDeeplink(deeplink: Uri) {
        _deeplink.update { deeplink }
    }

    private fun onDeeplinkConsumed() {
        _deeplink.update { null }
    }
}

sealed interface MainUiEvent {
    data class Deeplink(val deeplinkUri: Uri) : MainUiEvent
    data object DeeplinkConsumed : MainUiEvent
}

sealed interface UserAuthState {
    data object Loading : UserAuthState
    data object LoggedIn : UserAuthState
    data object LoggedOut : UserAuthState
}
