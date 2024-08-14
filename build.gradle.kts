plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)

    dependencies {
        detektPlugins(rootProject.libs.detekt.formatting)
    }

    detekt {
        config.setFrom(project.file("$rootDir/config/detekt/detekt.yml"))
        autoCorrect = true
        buildUponDefaultConfig = true
        parallel = true
    }
}

apply(from = "buildscripts/setup.gradle")

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
