package com.ch13mob.template.feature.quotes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch13mob.template.core.model.Quote

@Composable
fun QuotesList(
    modifier: Modifier = Modifier,
    quotes: List<Quote>,
    onQuoteClick: (quoteId: Int, quoteText: String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(count = quotes.size) { index ->
            QuoteCard(
                quote = quotes[index],
                onQuoteClick = { id, quoteText ->
                    onQuoteClick(id, quoteText)
                }
            )
        }
    }
}
