package template

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import template.core.data.repository.UserDataRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {

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
}

sealed interface UserAuthState {
    data object Loading : UserAuthState
    data object LoggedIn : UserAuthState
    data object LoggedOut : UserAuthState
}
