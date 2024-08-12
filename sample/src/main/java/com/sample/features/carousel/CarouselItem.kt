package com.sample.features.carousel

import androidx.annotation.DrawableRes

data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
//    @StringRes val contentDescriptionResId: Int
)
