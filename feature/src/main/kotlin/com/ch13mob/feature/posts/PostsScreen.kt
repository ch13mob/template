package com.ch13mob.feature.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ch13mob.feature.posts.component.EmptyPostsPlaceholder
import com.ch13mob.feature.posts.component.PostList
import com.ch13mob.template.core.designsystem.component.ProgressIndicator
import com.ch13mob.template.core.model.Post
import com.ch13mob.template.core.ui.DevicePreviews
import com.ch13mob.template.core.ui.FontScalePreviews
import com.ch13mob.template.core.ui.OrientationPreviews
import com.ch13mob.template.core.ui.PostsPreviewParameterProvider
import com.ch13mob.template.core.ui.ThemePreviews

@Composable
fun PostsRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit,
) {
    val postsState by viewModel.postsState.collectAsStateWithLifecycle()
    val sessionState by viewModel.sessionState.collectAsStateWithLifecycle()
    val isSyncing by viewModel.isSyncing.collectAsStateWithLifecycle()

    PostsScreen(
        postsState = postsState,
        sessionState = sessionState,
        isSyncing = isSyncing,
        onEvent = viewModel::onEvent,
        onPostClick = onPostClick
    )
}

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    postsState: PostsUiState,
    sessionState: SessionUiState,
    isSyncing: Boolean,
    onEvent: (PostsUiEvent) -> Unit,
    onPostClick: (Int) -> Unit
) {
    val isPostsLoading = postsState is PostsUiState.Loading
    val isLogoutLoading = sessionState is SessionUiState.Loading

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(postsState is PostsUiState.Error) {
        when (postsState) {
            PostsUiState.Loading -> Unit
            is PostsUiState.Success -> Unit
            is PostsUiState.Error -> {
                postsState.errorMessage?.let { errorMessage ->
                    snackbarHostState.showSnackbar(
                        message = errorMessage,
                        duration = SnackbarDuration.Short
                    )
                    onEvent(PostsUiEvent.ErrorConsumed)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(text = "Posts")
                },
                actions = {
                    IconButton(onClick = { onEvent(PostsUiEvent.Logout) }) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            when (postsState) {
                PostsUiState.Loading -> Unit
                is PostsUiState.Error -> TODO()
                is PostsUiState.Success -> {
                    if (postsState.posts.isEmpty()) {
                        EmptyPostsPlaceholder(
                            modifier = Modifier.padding(paddingValues)
                        )
                    } else {
                        PostList(
                            modifier = Modifier.padding(paddingValues),
                            posts = postsState.posts,
                            onPostClick = onPostClick,
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = isSyncing || isLogoutLoading || isPostsLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ProgressIndicator()
            }
        }
    )
}

@DevicePreviews
@FontScalePreviews
@OrientationPreviews
@ThemePreviews
@Composable
fun PostsScreenPreview(
    @PreviewParameter(PostsPreviewParameterProvider::class)
    posts: List<Post>
) {
    PostsScreen(
        postsState = PostsUiState.Success(posts = posts),
        sessionState = SessionUiState.LoggedIn,
        isSyncing = false,
        onEvent = {},
        onPostClick = {}
    )
}
