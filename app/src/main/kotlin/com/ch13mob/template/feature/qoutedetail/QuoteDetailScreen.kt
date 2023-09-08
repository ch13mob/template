package com.ch13mob.template.feature.qoutedetail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun QuoteDetailRoute(
    viewModel: QuoteDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val quoteId = viewModel.quoteId

    QuoteDetailScreen(
        quoteId = quoteId,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongMethod")
@Composable
fun QuoteDetailScreen(
    quoteId: Int,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(text = "Quote Detail")
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Quote ID: $quoteId"
                )
                Log.d("tux", "Quote ID: $quoteId")
            }
        }
    )
}
