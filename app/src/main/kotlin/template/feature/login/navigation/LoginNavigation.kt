package template.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import template.core.common.error.Error
import template.feature.login.LoginRoute

const val LoginNavigationGraph = "login_graph"
const val LoginNavigationRoute = "login_route"

fun NavGraphBuilder.loginScreen(
    onError: (Error) -> Unit,
) {
    composable(route = LoginNavigationRoute) {
        LoginRoute(
            onError = onError
        )
    }
}
