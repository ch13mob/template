package com.ch13mob.sample.features

import com.ch13mob.sample.Screen

enum class Feature(
    val title: String,
    val screen: Screen
) {
    ANIMATED_COUNTER(
        title = "Animated counter",
        screen = Screen.AnimatedCounter
    ),
    ANNOTATED_STRING(
        title = "Annotated String",
        screen = Screen.AnnotatedString
    )
}
