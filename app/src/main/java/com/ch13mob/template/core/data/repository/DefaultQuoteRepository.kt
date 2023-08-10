package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.data.mapper.toEntity
import com.ch13mob.template.core.data.mapper.toModel
import com.ch13mob.template.core.database.dao.QuoteDao
import com.ch13mob.template.core.model.Quote
import com.ch13mob.template.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultQuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val networkDataSource: NetworkDataSource
) : QuoteRepository {

    override suspend fun fetchQuotes() {
        val quotes = networkDataSource.getQuotes()
        quoteDao.updateQuotes(quotes.map { it.toEntity() })
    }

    override fun getQuotesStream(): Flow<List<Quote>> {
        return quoteDao.getQuotes().map { quotes ->
            if (quotes.isEmpty()) {
                return@map emptyList<Quote>()
            }

            return@map quotes.map { it.toModel() }
        }
    }
}
