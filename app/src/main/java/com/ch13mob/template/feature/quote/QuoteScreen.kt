package com.ch13mob.template.feature.quote

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ch13mob.template.R
import com.ch13mob.template.core.model.Quote

@Composable
fun QuoteRoute(
    viewModel: QuoteViewModel = hiltViewModel(),
    onQuoteClick: (quoteId: Int, quoteAuthor: String) -> Unit,
) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()
    val quote by viewModel.quote.collectAsStateWithLifecycle()
    val showDate by viewModel.showDate.collectAsStateWithLifecycle()

    QuoteScreen(
        isLoading = isLoading,
        errorMessage = errorMessage,
        quote = quote,
        showDate = showDate,
        onQuoteClick = onQuoteClick,
        nextQuoteClick = viewModel::refreshQuote,
        toggleShowDate = viewModel::toggleShowDate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    isLoading: Boolean,
    errorMessage: String?,
    quote: Quote,
    showDate: Boolean,
    onQuoteClick: (quoteId: Int, quoteAuthor: String) -> Unit,
    nextQuoteClick: () -> Unit,
    toggleShowDate: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.5f),
                    contentAlignment = Alignment.Center,
                ) {
                    if (quote.quote.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = "No quote found")
                        }
                    } else {
                        Card(
                            onClick = {
                                onQuoteClick(
                                    quote.id,
                                    quote.author
                                )
                            },
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .animateContentSize(),
                                horizontalAlignment = Alignment.Start,
                            ) {
                                Text(
                                    text = "${quote.author}:",
                                    style = MaterialTheme.typography.headlineMedium,
                                )
                                Text(
                                    text = "${quote.quote}",
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                if (showDate) {
                                    Text(
                                        modifier = Modifier
                                            .padding(top = 26.dp),
                                        text = quote.lastUpdatedTime()
                                    )
                                }
                            }
                        }
                    }

                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                Button(onClick = nextQuoteClick) {
                    Text(text = "Next quote")
                }
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Show date")
                    Spacer(
                        modifier = Modifier
                            .width(16.dp)
                    )
                    Switch(
                        checked = showDate,
                        onCheckedChange = { toggleShowDate() }
                    )
                }
            }
        }
    )
}
