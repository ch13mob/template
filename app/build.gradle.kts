import com.android.build.api.dsl.DefaultConfig
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.ch13mob.template"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.ch13mob.template"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigString(
            key = "BASE_URL",
            value = "https://api.breakingbadquotes.xyz/"
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.coroutines.android)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.runtime.livedata)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.dataStore.preferences)

    detektPlugins(libs.detekt.formatting)
}

detekt {
    config.setFrom(project.file("$rootDir/config/detekt/detekt.yml"))
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = true
}

fun DefaultConfig.buildConfigString(
    key: String,
    value: String,
) {
    buildConfigField("String", key, "\"$value\"")
}
