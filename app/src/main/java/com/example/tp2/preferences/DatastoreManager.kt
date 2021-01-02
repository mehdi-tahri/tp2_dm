package com.example.tp2.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DatastoreManager(context: Context) {
    val dataStore = context.createDataStore("user")

    companion object PreferencesKeys {
        val USER_APP_NAME = preferencesKey<String>("user_app_name")
        val CHANGE_COLOR_APPBAR = preferencesKey<String>("change_color_appbar")
        val CHANGE_COLOR_TEXT = preferencesKey<String>("change_color_text")
        lateinit var INSTANCE: DatastoreManager
    }

    val preferenceDataStore = object : PreferenceDataStore() {
        private val coroutineScope = MainScope()

        override fun putString(key: String, value: String?) {
            coroutineScope.launch {
                dataStore.edit {
                    it[preferencesKey<String>(key)] = value ?: ""
                }
            }
        }

        override fun getString(key: String, defValue: String?): String? {
            return runBlocking {
                dataStore.data.first()[preferencesKey(key)] ?: defValue
            }
        }

        override fun putBoolean(key: String, value: Boolean) {}
        override fun putFloat(key: String?, value: Float) {}
        override fun putInt(key: String?, value: Int) {}
        override fun putLong(key: String?, value: Long) {}
        override fun putStringSet(key: String?, values: MutableSet<String>?) {}
    }
}