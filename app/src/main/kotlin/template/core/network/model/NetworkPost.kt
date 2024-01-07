package template.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPost(
    @SerialName("id") val id: Int,
    @SerialName("userId") val userId: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String
)
