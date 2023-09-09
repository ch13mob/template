package com.ch13mob.template.core.database.di

import com.ch13mob.template.core.database.AppDatabase
import com.ch13mob.template.core.database.dao.PostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesPostDao(
        database: AppDatabase,
    ): PostDao = database.postDao()
}
