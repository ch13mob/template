package com.sample

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Features : Screen()

    @Serializable
    data object AnimatedCounter : Screen()

    @Serializable
    data object AnnotatedString : Screen()

    @Serializable
    data object PhotoPicker : Screen()

    @Serializable
    data object TextSelection : Screen()

    @Serializable
    data object MarqueeText : Screen()

    @Serializable
    data object WebView : Screen()

    @Serializable
    data object TabRowHorizontalPager : Screen()

    @Serializable
    data object DocumentScanner : Screen()

    @Serializable
    data object Carousel : Screen()
}
