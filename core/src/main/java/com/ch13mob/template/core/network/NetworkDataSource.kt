package com.ch13mob.template.core.network

import com.ch13mob.template.core.network.model.NetworkPost

interface NetworkDataSource {
    suspend fun getPosts(): List<NetworkPost>
}
