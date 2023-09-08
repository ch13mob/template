package com.ch13mob.template.feature.qoutedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ch13mob.template.feature.qoutedetail.QuoteDetailRoute

const val QuoteDetailNavigationRoute = "quote_detail_route"
const val QuoteIdArg = "quoteId"

fun NavController.navigateToQuoteDetail(
    quoteId: Int = -1,
    navOptions: NavOptions? = null
) {
    this.navigate(
        QuoteDetailNavigationRoute.plus("/$quoteId"),
        navOptions
    )
}

fun NavGraphBuilder.quoteDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = QuoteDetailNavigationRoute.plus("/{$QuoteIdArg}"),
        arguments = listOf(
            navArgument(QuoteIdArg) { type = NavType.IntType },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://template.com/{$QuoteIdArg}"
            }
        ),
    ) {
        QuoteDetailRoute(
            onBackClick = onBackClick
        )
    }
}
