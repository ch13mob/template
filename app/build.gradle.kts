plugins {
    id("template.android.application")
    id("template.android.application.compose")
    id("template.android.application.flavors")
    id("template.kotlin.detekt")
    id("template.android.hilt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    namespace = "com.ch13mob.template"

    defaultConfig {
        applicationId = "com.ch13mob.template"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    detektPlugins(libs.detekt.formatting)
}

detekt {
    config.setFrom(project.file("$rootDir/config/detekt/detekt.yml"))
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = true
}
