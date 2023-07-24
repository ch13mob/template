package com.ch13mob.template.core.data.di

import com.ch13mob.template.core.data.repository.AppQuoteRepository
import com.ch13mob.template.core.data.repository.AppUserDataRepository
import com.ch13mob.template.core.data.repository.QuoteRepository
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
        userDataRepository: AppUserDataRepository,
    ): UserDataRepository

    @Binds
    fun bindsQuoteRepository(
        quoteRepository: AppQuoteRepository,
    ): QuoteRepository
}
