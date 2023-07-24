package com.ch13mob.template.feature.qoutedetail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ch13mob.template.feature.qoutedetail.QuoteDetailRoute

const val QuoteDetailNavigationRoute = "quote_detail_route"

const val QuoteIdArg = "quoteId"
const val QuoteAuthorArg = "quoteAuthor"

fun NavController.navigateToQuoteDetail(
    quoteId: Int = -1,
    quoteAuthor: String = "",
    navOptions: NavOptions? = null
) {
    this.navigate(
        QuoteDetailNavigationRoute.plus("/$quoteId&$quoteAuthor"),
        navOptions
    )
}

fun NavGraphBuilder.quoteDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = QuoteDetailNavigationRoute.plus("/{$QuoteIdArg}&{$QuoteAuthorArg}"),
        arguments = listOf(
            navArgument(QuoteIdArg) { type = NavType.IntType },
            navArgument(QuoteAuthorArg) { type = NavType.StringType }
        )
    ) {
        QuoteDetailRoute(
            onBackClick = onBackClick
        )
    }
}
