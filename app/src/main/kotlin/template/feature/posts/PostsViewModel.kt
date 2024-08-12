package template.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import template.core.common.error.Error
import template.core.common.result.fold
import template.core.data.repository.PostRepository
import template.core.data.repository.UserDataRepository
import template.core.model.Post
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState(isLoading = true))
    val uiState: StateFlow<PostsUiState> = _uiState.asStateFlow()

    init {
        getPosts()
    }

    fun onEvent(event: PostsUiEvent) {
        when (event) {
            is PostsUiEvent.Logout -> onLogoutClick()
            is PostsUiEvent.ErrorConsumed -> onErrorConsumed()
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            postRepository.getPosts()
                .collect { result ->
                    _uiState.update { it.copy(isLoading = false) }

                    result.fold(
                        onSuccess = { posts ->
                            _uiState.update { it.copy(posts = posts) }
                        },
                        onFailure = { error ->
                            _uiState.update { it.copy(error = error) }
                        }
                    )
                }
        }
    }

    private fun onLogoutClick() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userDataRepository.logout()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun onErrorConsumed() {
        _uiState.update { it.copy(error = null) }
    }
}

sealed interface PostsUiEvent {
    data object Logout : PostsUiEvent
    data object ErrorConsumed : PostsUiEvent
}

data class PostsUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: Error? = null
)
