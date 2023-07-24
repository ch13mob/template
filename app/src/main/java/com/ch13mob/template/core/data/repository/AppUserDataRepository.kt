package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.datastore.AppPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppUserDataRepository @Inject constructor(
    private val preferencesDataSource: AppPreferencesDataSource
) : UserDataRepository {

    override val showDate: Flow<Boolean>
        get() = preferencesDataSource.showDate

    override suspend fun toggleShowDate() {
        preferencesDataSource.toggleShowDate()
    }
}
