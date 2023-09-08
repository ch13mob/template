package com.ch13mob.template.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ch13mob.template.core.database.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query(value = "SELECT * FROM quote WHERE id =:id")
    fun getQuote(id: Int): Flow<QuoteEntity?>

    @Query(value = "SELECT * FROM quote")
    fun getQuotes(): Flow<List<QuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuotes(quotes: List<QuoteEntity>)

    @Query(value = "DELETE FROM quote")
    suspend fun deleteAllQuotes()

    @Transaction
    suspend fun updateQuotes(quotes: List<QuoteEntity>) {
        deleteAllQuotes()
        insertQuotes(quotes)
    }
}
