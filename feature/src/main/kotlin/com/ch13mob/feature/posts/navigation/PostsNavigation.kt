package com.ch13mob.feature.posts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ch13mob.feature.posts.PostsRoute

const val PostsNavigationGraph = "posts_graph"
const val PostsNavigationRoute = "posts_route"

fun NavGraphBuilder.postsScreen(
    onPostClick: (Int) -> Unit,
) {
    composable(route = com.ch13mob.feature.posts.navigation.PostsNavigationRoute) {
        PostsRoute(
            onPostClick = onPostClick
        )
    }
}