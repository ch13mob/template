package com.ch13mob.template.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "quote"
)
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val author: String,
    val date: Instant
)
