package template.core.data.repository

import kotlinx.coroutines.flow.Flow
import template.core.model.UserData

interface UserDataRepository {
    val userData: Flow<UserData>
    suspend fun login(email: String, password: String)
    suspend fun logout()
}
