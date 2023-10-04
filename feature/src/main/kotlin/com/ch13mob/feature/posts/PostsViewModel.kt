package com.ch13mob.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.template.core.data.repository.PostRepository
import com.ch13mob.template.core.data.repository.UserDataRepository
import com.ch13mob.template.core.data.util.SyncManager
import com.ch13mob.template.core.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    syncManager: SyncManager,
    private val postRepository: PostRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _sessionState = MutableStateFlow<SessionUiState>(SessionUiState.LoggedIn)
    val sessionState = _sessionState.asStateFlow()

    val isSyncing = syncManager.isSyncing
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val postsState: StateFlow<PostsUiState> = postRepository.getPosts()
        .map(PostsUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PostsUiState.Loading
        )

    fun onEvent(event: PostsUiEvent) {
        when (event) {
            PostsUiEvent.ErrorConsumed -> onErrorConsumed()
            PostsUiEvent.Logout -> logout()
        }
    }

    private fun onErrorConsumed() {
        // TODO
    }

    private fun logout() {
        viewModelScope.launch {
            _sessionState.update { SessionUiState.Loading }
            userDataRepository.logout()
            _sessionState.update { SessionUiState.Logout }
        }
    }
}

sealed interface PostsUiEvent {
    data object ErrorConsumed : PostsUiEvent
    data object Logout : PostsUiEvent
}

sealed interface PostsUiState {
    data object Loading : PostsUiState
    data class Error(val errorMessage: String?) : PostsUiState
    data class Success(val posts: List<Post>) : PostsUiState
}

sealed interface SessionUiState {
    data object Loading : SessionUiState
    data class Error(val errorMessage: String?) : SessionUiState
    data object LoggedIn : SessionUiState
    data object Logout : SessionUiState
}
