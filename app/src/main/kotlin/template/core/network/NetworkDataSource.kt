package template.core.network

import template.core.network.model.NetworkPost

interface NetworkDataSource {
    suspend fun getPosts(): List<NetworkPost>
}
