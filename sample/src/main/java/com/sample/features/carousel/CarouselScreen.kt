package com.sample.features.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sample.R

private val items = listOf(
    CarouselItem(0, R.drawable.image_1),
    CarouselItem(1, R.drawable.image_2),
    CarouselItem(2, R.drawable.image_3),
    CarouselItem(3, R.drawable.image_4),
    CarouselItem(4, R.drawable.image_5)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "HorizontalMultiBrowseCarousel",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalMultiBrowseCarousel(
            modifier = Modifier
                .width(412.dp)
                .height(221.dp),
            state = rememberCarouselState { items.count() },
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { index ->
            val item = items[index]

            Image(
                modifier = Modifier
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Gray),
                painter = painterResource(id = item.imageResId),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = "HorizontalUncontainedCarousel",
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalUncontainedCarousel(
            modifier = Modifier
                .width(412.dp)
                .height(221.dp),
            state = rememberCarouselState { items.count() },
            itemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = items[i]
            Image(
                modifier = Modifier
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge)
                    .background(Color.Gray),
                painter = painterResource(id = item.imageResId),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
    }
}
