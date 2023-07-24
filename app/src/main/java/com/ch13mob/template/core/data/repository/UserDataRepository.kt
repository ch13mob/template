package com.ch13mob.template.core.data.repository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val showDate: Flow<Boolean>
    suspend fun toggleShowDate()
}
