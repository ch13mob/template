package com.ch13mob.template

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.BaseFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    main
}

@Suppress("EnumEntryName")
enum class AppFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val baseUrl: String
) {
    dev(
        dimension = FlavorDimension.main,
        applicationIdSuffix = ".dev",
        baseUrl = "https://jsonplaceholder.typicode.com/"
    ),
    qa(
        dimension = FlavorDimension.main,
        applicationIdSuffix = ".qa",
        baseUrl = "https://jsonplaceholder.typicode.com/"
    ),
    prod(
        dimension = FlavorDimension.main,
        baseUrl = "https://jsonplaceholder.typicode.com/"
    )
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: AppFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.main.name
        productFlavors {
            AppFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    buildConfigString(
                        key = "BASE_URL",
                        value = it.baseUrl
                    )
                    flavorConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            this.applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}

fun BaseFlavor.buildConfigString(
    key: String,
    value: String,
) {
    buildConfigField("String", key, "\"$value\"")
}
