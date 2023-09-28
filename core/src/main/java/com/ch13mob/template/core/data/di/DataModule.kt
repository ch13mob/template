package com.ch13mob.template.core.data.di

import com.ch13mob.template.core.data.repository.DefaultPostRepository
import com.ch13mob.template.core.data.repository.DefaultUserDataRepository
import com.ch13mob.template.core.data.repository.PostRepository
import com.ch13mob.template.core.data.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
}
