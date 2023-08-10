package com.ch13mob.template.feature.quotes

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ch13mob.template.R
import com.ch13mob.template.core.designsystem.component.ProgressIndicator
import com.ch13mob.template.feature.quotes.component.EmptyQuotesPlaceholder
import com.ch13mob.template.feature.quotes.component.QuotesList

@Composable
fun QuotesRoute(
    viewModel: QuotesViewModel = hiltViewModel(),
    onQuoteClick: (quoteId: Int, quoteText: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    QuotesScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onQuoteClick = onQuoteClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuotesScreen(
    uiState: QuotesUiState,
    onEvent: (QuotesUiEvent) -> Unit,
    onQuoteClick: (quoteId: Int, quoteText: String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage != null) {
        uiState.errorMessage?.let { errorMessage ->
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Short
            )
            onEvent(QuotesUiEvent.ErrorConsumed)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onEvent(QuotesUiEvent.RefreshQuotes) },
                expanded = true,
                icon = {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = null
                    )
                },
                text = { Text(text = "Refresh Quotes") },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            if (uiState.quotes.isEmpty()) {
                EmptyQuotesPlaceholder(
                    modifier = Modifier.padding(paddingValues)
                )
            } else {
                QuotesList(
                    modifier = Modifier.padding(paddingValues),
                    quotes = uiState.quotes,
                    onQuoteClick = onQuoteClick,
                )
            }

            if (uiState.isLoading) {
                ProgressIndicator()
            }
        }
    )
}
