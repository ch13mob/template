package com.ch13mob.feature.postdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ch13mob.feature.postdetail.PostDetailRoute

const val PostDetailNavigationRoute = "post_detail_route"
const val PostIdArg = "postId"

fun NavController.navigateToPostDetail(
    quoteId: Int = -1,
    navOptions: NavOptions? = null
) {
    this.navigate(
        com.ch13mob.feature.postdetail.navigation.PostDetailNavigationRoute.plus("/$quoteId"),
        navOptions
    )
}

fun NavGraphBuilder.postDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = com.ch13mob.feature.postdetail.navigation.PostDetailNavigationRoute.plus("/{${com.ch13mob.feature.postdetail.navigation.PostIdArg}}"),
        arguments = listOf(
            navArgument(com.ch13mob.feature.postdetail.navigation.PostIdArg) { type = NavType.IntType },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://template.com/{${com.ch13mob.feature.postdetail.navigation.PostIdArg}}"
            }
        ),
    ) {
        PostDetailRoute(
            onBackClick = onBackClick
        )
    }
}
