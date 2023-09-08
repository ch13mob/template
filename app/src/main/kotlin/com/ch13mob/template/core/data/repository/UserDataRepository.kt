package com.ch13mob.template.core.data.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val isLoggedIn: Flow<Boolean>
    suspend fun login(email: String, password: String)
    suspend fun logout()
}
