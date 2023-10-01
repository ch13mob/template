package com.ch13mob.template.core.data.repository

import com.ch13mob.template.core.datastore.AppPreferencesDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserDataRepository @Inject constructor(
    private val preferencesDataSource: AppPreferencesDataSource
) : UserDataRepository {

    override val isLoggedIn: Flow<Boolean>
        get() = preferencesDataSource.isLoggedIn

    override suspend fun login(email: String, password: String) {
        delay(DELAY)
        preferencesDataSource.setEmail(email)
        preferencesDataSource.setPassword(password)
    }

    override suspend fun logout() {
        delay(DELAY)
        preferencesDataSource.setEmail("")
        preferencesDataSource.setPassword("")
    }

    companion object {
        private const val DELAY = 2000L
    }
}
