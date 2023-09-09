package com.ch13mob.template.feature.posts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ch13mob.template.feature.posts.PostsRoute

const val PostsNavigationGraph = "posts_graph"
const val PostsNavigationRoute = "posts_route"

fun NavGraphBuilder.postsScreen(
    onPostClick: (Int) -> Unit,
) {
    composable(route = PostsNavigationRoute) {
        PostsRoute(
            onPostClick = onPostClick
        )
    }
}
