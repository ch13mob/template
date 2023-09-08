package com.ch13mob.template.core.data.mapper

import com.ch13mob.template.core.database.model.QuoteEntity
import com.ch13mob.template.core.model.Quote
import com.ch13mob.template.core.network.model.NetworkQuote
import kotlinx.datetime.Clock

fun NetworkQuote.toEntity() = QuoteEntity(
    id = 0,
    text = text,
    author = author,
    date = Clock.System.now()
)

fun QuoteEntity.toModel() = Quote(
    id = this.id,
    text = this.text,
    author = this.author,
    date = this.date
)
