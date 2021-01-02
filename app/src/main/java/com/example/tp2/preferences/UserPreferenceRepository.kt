package com.example.tp2.preferences

import com.example.tp2.preferences.DatastoreManager.PreferencesKeys.CHANGE_COLOR_APPBAR
import com.example.tp2.preferences.DatastoreManager.PreferencesKeys.CHANGE_COLOR_TEXT
import com.example.tp2.preferences.DatastoreManager.PreferencesKeys.USER_APP_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferenceRepository() {
    private val dataStore = DatastoreManager.INSTANCE.dataStore

    val userAppNameFlow: Flow<String> = dataStore.data
        .map { preferences ->
            preferences[USER_APP_NAME]?: ""
        }
    val colorAppBarFlow: Flow<String> = dataStore.data
            .map { preferences ->
                preferences[CHANGE_COLOR_APPBAR]?: ""
            }
    val colorTextFlow: Flow<String> = dataStore.data
            .map { preferences ->
                preferences[CHANGE_COLOR_TEXT]?: ""
            }

}