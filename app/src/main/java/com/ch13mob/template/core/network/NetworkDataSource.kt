package com.ch13mob.template.core.network

import com.ch13mob.template.core.network.model.NetworkQuote

interface NetworkDataSource {
    suspend fun getRandomQuote(): List<NetworkQuote>
}
