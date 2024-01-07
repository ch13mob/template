package template.feature.postdetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import template.feature.postdetail.PostDetailRoute

const val PostDetailNavigationRoute = "post_detail_route"
const val PostIdArg = "postId"

fun NavController.navigateToPostDetail(
    quoteId: Int = -1,
    navOptions: NavOptions? = null
) {
    this.navigate(
        PostDetailNavigationRoute.plus("/$quoteId"),
        navOptions
    )
}

fun NavGraphBuilder.postDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = PostDetailNavigationRoute.plus(
            "/{$PostIdArg}"
        ),
        arguments = listOf(
            navArgument(PostIdArg) { type = NavType.IntType },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://template.com/{$PostIdArg}"
            }
        ),
    ) {
        PostDetailRoute(
            onBackClick = onBackClick
        )
    }
}
