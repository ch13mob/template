package template.feature.posts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import template.core.model.Post
import template.core.ui.component.AppErrorSnackbar
import template.core.ui.component.AppProgressIndicator
import template.core.ui.preview.DevicePreviews
import template.core.ui.preview.FontScalePreviews
import template.core.ui.preview.OrientationPreviews
import template.core.ui.preview.PostsPreviewParameterProvider
import template.core.ui.preview.ThemePreviews
import template.feature.posts.component.EmptyPostsPlaceholder
import template.feature.posts.component.PostList

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

    AppErrorSnackbar(
        error = uiState.error,
        onErrorConsumed = { onEvent(PostsUiEvent.ErrorConsumed) }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(text = "Posts")
            },
            actions = {
                IconButton(
                    onClick = { onEvent(PostsUiEvent.Logout) }
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.Logout,
                        contentDescription = "Logout button"
                    )
                }
            }
        )

        if (uiState.posts.isEmpty()) {
            EmptyPostsPlaceholder()
        } else {
            PostList(
                posts = uiState.posts,
                onPostClick = onPostClick,
            )
        }
    }

    AnimatedVisibility(
        visible = uiState.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        AppProgressIndicator()
    }
}

@Preview(showSystemUi = true)
@DevicePreviews
@FontScalePreviews
@OrientationPreviews
@ThemePreviews
@Composable
private fun PostsScreenPreview(
    @PreviewParameter(PostsPreviewParameterProvider::class)
    posts: List<Post>
) {
    PostsScreen(
        uiState = PostsUiState(posts = posts),
        onEvent = {},
        onPostClick = {}
    )
}
