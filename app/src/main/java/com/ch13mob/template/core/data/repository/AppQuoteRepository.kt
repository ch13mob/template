package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.data.mapper.toEntity
import com.ch13mob.template.core.data.mapper.toModel
import com.ch13mob.template.core.database.dao.QuoteDao
import com.ch13mob.template.core.model.Quote
import com.ch13mob.template.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppQuoteRepository @Inject constructor(
    private val quoteDao: QuoteDao,
    private val networkDataSource: NetworkDataSource
) : QuoteRepository {

    override suspend fun fetchQuote() {
        val quote = networkDataSource.getRandomQuote().first()
        quoteDao.insertQuote(quote.toEntity())
    }

    override fun getQuote(id: Int): Flow<Quote> {
        return quoteDao.getQuote(id).map { quoteEntity ->
            if (quoteEntity == null) {
                return@map Quote()
            }

            quoteEntity.toModel()
        }
    }
}
