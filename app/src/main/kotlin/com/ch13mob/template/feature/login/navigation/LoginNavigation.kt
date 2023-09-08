package com.ch13mob.template.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ch13mob.template.feature.login.LoginRoute

const val LoginNavigationGraph = "login_graph"
const val LoginNavigationRoute = "login_route"

fun NavGraphBuilder.loginScreen() {
    composable(route = LoginNavigationRoute) {
        LoginRoute()
    }
}
