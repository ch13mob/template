package com.ch13mob.template.core.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

class AppPreferencesDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.dataStore

    val showDate = dataStore.data.map { preferences ->
        preferences[showDateKey] ?: true
    }

    suspend fun toggleShowDate() {
        dataStore.edit { preferences ->
            preferences[showDateKey] = preferences[showDateKey]?.not() ?: false
        }
    }

    companion object {
        private const val SHOW_DATE = "show_date"
        private val showDateKey = booleanPreferencesKey(SHOW_DATE)
    }
}
