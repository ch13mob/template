package template.core.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import template.core.datastore.AppPreferencesDataSource
import template.core.model.UserData
import javax.inject.Inject

class DefaultUserDataRepository @Inject constructor(
    private val preferencesDataSource: AppPreferencesDataSource
) : UserDataRepository {

    override val userData: Flow<UserData>
        get() = preferencesDataSource.userData

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
