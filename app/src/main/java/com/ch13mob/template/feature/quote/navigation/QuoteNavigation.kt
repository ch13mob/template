package com.ch13mob.template.feature.quote.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ch13mob.template.feature.quote.QuoteRoute

const val QuoteNavigationRoute = "quote_route"

fun NavGraphBuilder.quoteScreen(
    onQuoteClick: (quoteId: Int, quoteAuthor: String) -> Unit,
) {
    composable(route = QuoteNavigationRoute) {
        QuoteRoute(
            onQuoteClick = onQuoteClick
        )
    }
}
