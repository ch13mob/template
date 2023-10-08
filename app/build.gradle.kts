plugins {
    id("template.android.application")
    id("template.android.application.compose")
    id("template.android.application.flavors")
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
    implementation(projects.core)
    implementation(projects.feature)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
}
