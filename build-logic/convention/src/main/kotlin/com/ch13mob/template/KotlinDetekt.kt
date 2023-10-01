package com.ch13mob.template

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project

internal fun Project.configureDetekt(extension: DetektExtension) {
    extension.apply {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        parallel = true
        buildUponDefaultConfig = true
        autoCorrect = true
    }
}
