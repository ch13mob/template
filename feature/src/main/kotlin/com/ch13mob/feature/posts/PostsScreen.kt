package com.ch13mob.feature.posts

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
import com.ch13mob.template.core.designsystem.component.ProgressIndicator
import com.ch13mob.template.core.model.Post
import com.ch13mob.template.core.ui.DevicePreviews
import com.ch13mob.template.core.ui.FontScalePreviews
import com.ch13mob.template.core.ui.OrientationPreviews
import com.ch13mob.template.core.ui.PostsPreviewParameterProvider
import com.ch13mob.template.core.ui.ThemePreviews
import com.ch13mob.feature.posts.component.EmptyPostsPlaceholder
import com.ch13mob.feature.posts.component.PostList

@Composable
fun PostsRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PostsScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onPostClick = onPostClick
    )
}

@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    uiState: PostsUiState,
    onEvent: (PostsUiEvent) -> Unit,
    onPostClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage != null) {
        uiState.errorMessage?.let { errorMessage ->
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short
            )
            onEvent(PostsUiEvent.ErrorConsumed)
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
            if (uiState.posts.isEmpty()) {
                EmptyPostsPlaceholder(
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                PostList(
                    modifier = Modifier.padding(paddingValues),
                    posts = uiState.posts,
                    onPostClick = onPostClick,
                )
            }

            if (uiState.isLoading) {
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
        uiState = PostsUiState(posts = posts),
        onEvent = {},
        onPostClick = {}
    )
}
