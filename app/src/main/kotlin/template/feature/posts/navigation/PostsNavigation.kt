package template.feature.posts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import template.core.common.error.Error
import template.feature.posts.PostsRoute

const val PostsNavigationGraph = "posts_graph"
const val PostsNavigationRoute = "posts_route"

fun NavGraphBuilder.postsScreen(
    onError: (Error) -> Unit,
    onPostClick: (Int) -> Unit
) {
    composable(route = PostsNavigationRoute) {
        PostsRoute(
            onError = onError,
            onPostClick = onPostClick
        )
    }
}
