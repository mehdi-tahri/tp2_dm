package com.example.tp2.network

import android.app.Application
import androidx.preference.PreferenceDataStore
import com.example.tp2.preferences.DatastoreManager

class App: Application() {

    lateinit var customDatastore: PreferenceDataStore

    override fun onCreate() {
        super.onCreate()
        Api.INSTANCE = Api(this)
        DatastoreManager.INSTANCE = DatastoreManager(this)
    }
}