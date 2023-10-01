plugins {
    id("template.android.library")
    id("template.kotlin.detekt")
    id("template.android.hilt")
    id("template.android.room")
    id("template.android.library.compose")
    id("kotlinx-serialization")
}

android {
    namespace = "com.ch13mob.template.core"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    lint {
        checkDependencies = true
    }
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.dataStore.preferences)

    implementation(libs.coil.kt.compose)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}