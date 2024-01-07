package template.core.datastore.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import template.core.datastore.AppPreferencesDataSource
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
