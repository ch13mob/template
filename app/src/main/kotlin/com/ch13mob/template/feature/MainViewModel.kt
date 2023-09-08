package com.ch13mob.template.feature

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.template.core.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {

    private val _isLoggedInStream = userDataRepository.isLoggedIn
    private val _deeplinkUri = MutableStateFlow<Uri?>(null)

    val appState: StateFlow<MainUiState> = combine(
        _isLoggedInStream,
        _deeplinkUri
    ) { isLoggedIn, deeplinkUri ->
        MainUiState(
            isLoggedIn = isLoggedIn,
            deeplinkUri = deeplinkUri
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState()
        )

    fun onEvent(event: MainUiEvent) {
        when (event) {
            is MainUiEvent.HandleDeeplink -> handleDeeplink(event.deeplink)
            MainUiEvent.DeeplinkConsumed -> onDeeplinkConsumed()
        }
    }

    private fun handleDeeplink(deeplink: Uri) {
        _deeplinkUri.value = deeplink
    }

    private fun onDeeplinkConsumed() {
        _deeplinkUri.value = null
    }
}

sealed interface MainUiEvent {
    data class HandleDeeplink(val deeplink: Uri) : MainUiEvent
    object DeeplinkConsumed : MainUiEvent
}

data class MainUiState(
    val isLoggedIn: Boolean = true,
    val deeplinkUri: Uri? = null,
)
