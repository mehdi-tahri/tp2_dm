package com.example.tp2.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceRepository(context: Context) {
    private val dataStore = context.createDataStore(name = "user")

    companion object PreferencesKeys {
        val USER_APP_NAME = preferencesKey<String>("USER_APP_NAME")
    }

    val userAppNameFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[USER_APP_NAME]?: ""
        }

    suspend fun updateShowCompleted( appName: String) {
        dataStore.edit { preferences ->
            preferences[USER_APP_NAME] = appName
        }
    }
}