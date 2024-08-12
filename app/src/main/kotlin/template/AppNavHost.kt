package template

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import template.feature.launch.LaunchRoute
import template.feature.login.LoginRoute
import template.feature.postdetail.PostDetailRoute
import template.feature.posts.PostsRoute

@Serializable
sealed class NavigationGraph {
    @Serializable
    data object Login : NavigationGraph()

    @Serializable
    data object Posts : NavigationGraph()
}

@Serializable
sealed class Screen {
    @Serializable
    data object Launch : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object Posts : Screen()

    @Serializable
    data class PostDetail(val postId: Int) : Screen() {
        companion object {
            const val POST_ID_ARG = "postId"
            const val DEEPLINK_URI_PATTERN = "https://template.com/{$POST_ID_ARG}"
        }
    }
}

@Suppress("LongMethod")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    userAuth: UserAuthState,
    deeplink: Uri?,
    onDeeplinkConsumed: () -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(userAuth) {
        when (userAuth) {
            UserAuthState.LoggedIn -> {
                navController.navigate(NavigationGraph.Posts) { popUpTo(0) }
            }

            UserAuthState.LoggedOut -> {
                navController.navigate(NavigationGraph.Login) { popUpTo(0) }
            }

            else -> {
                navController.navigate(Screen.Launch) { popUpTo(0) }
            }
        }
    }

    LaunchedEffect(deeplink, userAuth) {
        deeplink?.let { deeplink ->
            if (userAuth is UserAuthState.LoggedIn) {
                navController.navigate(deeplink)
                onDeeplinkConsumed()
            }
        }
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Launch
    ) {
        composable<Screen.Launch> {
            LaunchRoute()
        }

        navigation<NavigationGraph.Login>(
            startDestination = Screen.Login
        ) {
            composable<Screen.Login> {
                LoginRoute()
            }
        }

        navigation<NavigationGraph.Posts>(
            startDestination = Screen.Posts,
        ) {
            composable<Screen.Posts> {
                PostsRoute(
                    onPostClick = { postId ->
                        navController.navigate(Screen.PostDetail(postId = postId))
                    }
                )
            }
            composable<Screen.PostDetail>(
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = Screen.PostDetail.DEEPLINK_URI_PATTERN
                    }
                ),
            ) {
                PostDetailRoute(onBackClick = navController::navigateUp)
            }
        }
    }
}
