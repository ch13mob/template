package com.ch13mob.template.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

class AppPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.dataStore

    val isLoggedIn = dataStore.data.map { preferences ->
        preferences[emailKey].isNullOrEmpty().not()
    }

    suspend fun setEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[emailKey] = email
        }
    }

    suspend fun setPassword(password: String) {
        dataStore.edit { preferences ->
            preferences[passwordKey] = password
        }
    }

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private val emailKey = stringPreferencesKey(EMAIL)
        private val passwordKey = stringPreferencesKey(PASSWORD)
    }
}
