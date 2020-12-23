package com.example.tp2.network

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Api.INSTANCE = Api(this)
    }
}