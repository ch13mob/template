package template.core.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import template.core.network.NetworkDataSource
import template.core.network.retrofit.RetrofitNetwork

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun bindsNetworkDataSource(
        retrofitNetwork: RetrofitNetwork,
    ): NetworkDataSource
}
