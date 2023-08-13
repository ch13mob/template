package com.ch13mob.template.feature.quotes.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ch13mob.template.core.model.Quote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteCard(
    modifier: Modifier = Modifier,
    quote: Quote,
    onQuoteClick: (Int) -> Unit,
) {
    Card(
        onClick = { onQuoteClick(quote.id) },
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(170.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "${quote.author}:",
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "${quote.text}",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .padding(top = 26.dp)
                    .align(Alignment.End),
                style = MaterialTheme.typography.bodySmall,
                text = quote.lastUpdatedTime()
            )
        }
    }
}
