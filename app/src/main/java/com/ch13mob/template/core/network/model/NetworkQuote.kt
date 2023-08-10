package com.ch13mob.template.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkQuote(
    @SerialName("quote") val text: String,
    @SerialName("author") val author: String
)
