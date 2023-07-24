package com.ch13mob.template.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch13mob.template.core.database.model.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query(value = "SELECT * FROM quote WHERE id =:id")
    fun getQuote(id: Int): Flow<QuoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)
}
