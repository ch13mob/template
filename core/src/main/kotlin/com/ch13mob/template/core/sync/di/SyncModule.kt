package com.ch13mob.template.core.sync.di

import com.ch13mob.template.core.data.util.SyncManager
import com.ch13mob.template.core.sync.status.StubSyncSubscriber
import com.ch13mob.template.core.sync.status.SyncSubscriber
import com.ch13mob.template.core.sync.status.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {
    @Binds
    fun bindsSyncStatusMonitor(
        syncStatusMonitor: WorkManagerSyncManager,
    ): SyncManager

    @Binds
    fun bindsSyncSubscriber(
        syncSubscriber: StubSyncSubscriber,
    ): SyncSubscriber
}
