package com.ch13mob.template.core.database.di

import com.ch13mob.template.core.database.AppDatabase
import com.ch13mob.template.core.database.dao.QuoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesQuoteDao(
        database: AppDatabase,
    ): QuoteDao = database.noteDao()
}
