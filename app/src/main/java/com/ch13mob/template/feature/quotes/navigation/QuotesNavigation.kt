package com.ch13mob.template.feature.quotes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ch13mob.template.feature.quotes.QuotesRoute

const val QuotesNavigationRoute = "quotes_route"

fun NavGraphBuilder.quotesScreen(
    onQuoteClick: (quoteId: Int, quoteText: String) -> Unit,
) {
    composable(route = QuotesNavigationRoute) {
        QuotesRoute(
            onQuoteClick = onQuoteClick
        )
    }
}
