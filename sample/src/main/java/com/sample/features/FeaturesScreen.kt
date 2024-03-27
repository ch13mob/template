package com.sample.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeaturesScreen(
    features: List<Feature>,
    onFeatureClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(features) { index, item ->
            ListItem(
                modifier = Modifier.clickable {
                    onFeatureClick(item.screen.route)
                },
                headlineContent = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            )

            if (index < features.lastIndex) {
                HorizontalDivider(thickness = 0.2.dp)
            }
        }
    }
}
