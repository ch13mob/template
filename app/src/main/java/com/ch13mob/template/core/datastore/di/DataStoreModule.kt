package com.ch13mob.template.core.datastore.di

import android.content.Context
import com.ch13mob.template.core.datastore.AppPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesAppPreferencesDataStore(
        @ApplicationContext context: Context
    ): AppPreferencesDataSource = AppPreferencesDataSource(context)
}
