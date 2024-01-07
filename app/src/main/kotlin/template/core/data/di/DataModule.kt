package template.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import template.core.data.repository.DefaultPostRepository
import template.core.data.repository.DefaultUserDataRepository
import template.core.data.repository.PostRepository
import template.core.data.repository.UserDataRepository
import template.core.data.util.ConnectivityManagerNetworkMonitor
import template.core.data.util.NetworkMonitor

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserDataRepository(
        userDataRepository: DefaultUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsPostRepository(
        postRepository: DefaultPostRepository,
    ): PostRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
