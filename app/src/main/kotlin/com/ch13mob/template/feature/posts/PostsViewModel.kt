package com.ch13mob.template.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.template.core.common.exception.message
import com.ch13mob.template.core.data.repository.PostRepository
import com.ch13mob.template.core.data.repository.UserDataRepository
import com.ch13mob.template.core.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _postsStream =
        postRepository.getPosts().catch { exception ->
            _errorMessage.update { exception.message() }
            _isLoading.update { false }
        }

    init {
        fetchPosts()
    }

    val uiState: StateFlow<PostsUiState> = combine(
        _postsStream,
        _isLoading,
        _errorMessage
    ) { posts, isLoading, errorMessage ->
        PostsUiState(
            posts = posts,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PostsUiState(isLoading = true)
        )

    fun onEvent(event: PostsUiEvent) {
        when (event) {
            PostsUiEvent.RefreshPosts -> fetchPosts()
            PostsUiEvent.ErrorConsumed -> onErrorConsumed()
            PostsUiEvent.Logout -> logout()
        }
    }

    private fun onErrorConsumed() {
        _errorMessage.update { null }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            runCatching {
                _isLoading.update { true }
                postRepository.fetchPosts()
                _isLoading.update { false }
            }.onFailure { exception ->
                _errorMessage.update { exception.message() }
                _isLoading.update { false }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _isLoading.update { true }
            userDataRepository.logout()
            _isLoading.update { false }
        }
    }
}

sealed interface PostsUiEvent {
    data object RefreshPosts : PostsUiEvent
    data object ErrorConsumed : PostsUiEvent
    data object Logout : PostsUiEvent
}

data class PostsUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
