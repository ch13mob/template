package com.ch13mob.sample

sealed class Screen(val route: String) {
    data object Features : Screen("home")
    data object AnimatedCounter : Screen("counter")
}
