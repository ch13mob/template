package com.sample.features.marqueetext

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarqueeTextScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .background(Color.Red)
                .padding(vertical = 12.dp)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    initialDelayMillis = 0
                ),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            color = Color.White
        )
    }
}
