package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun fetchQuote()
    fun getQuote(id: Int): Flow<Quote>
}
