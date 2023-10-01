package com.ch13mob.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ch13mob.feature.login.LoginRoute

const val LoginNavigationGraph = "login_graph"
const val LoginNavigationRoute = "login_route"

fun NavGraphBuilder.loginScreen() {
    composable(route = com.ch13mob.feature.login.navigation.LoginNavigationRoute) {
        LoginRoute()
    }
}
