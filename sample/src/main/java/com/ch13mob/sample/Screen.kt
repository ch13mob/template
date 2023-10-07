package com.ch13mob.sample

sealed class Screen(val route: String) {
    data object Features : Screen("home")
    data object AnimatedCounter : Screen("counter")
    data object AnnotatedString : Screen("terms")
    data object PhotoPicker : Screen("photo_picker")
    data object TextSelection : Screen("text_selection")
    data object MarqueeText : Screen("marquee_text")
}
