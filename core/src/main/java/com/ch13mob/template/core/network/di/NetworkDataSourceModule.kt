package com.ch13mob.template.core.network.di

import com.ch13mob.template.core.network.NetworkDataSource
import com.ch13mob.template.core.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkDataSourceModule {

    @Binds
    fun bindsNetworkDataSource(
        retrofitNetwork: RetrofitNetwork,
    ): NetworkDataSource
}
