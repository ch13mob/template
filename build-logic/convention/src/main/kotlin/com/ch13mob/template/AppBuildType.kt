package com.ch13mob.template

@Suppress("unused")
enum class AppBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}
