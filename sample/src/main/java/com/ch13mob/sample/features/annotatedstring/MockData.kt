package com.ch13mob.sample.features.annotatedstring

object MockData {

    object PrivacyPolicy {
        const val fullText =
            "By clicking on the button below, you agree to our Terms of Service and Privacy Policy."
        val links = listOf(
            Link(
                text = "Terms of Service",
                url = "https://google.com"
            ),
            Link(
                text = "Privacy Policy",
                url = "https://google.com"
            )
        )
    }
}
