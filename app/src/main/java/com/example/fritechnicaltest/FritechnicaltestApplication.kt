package com.example.fritechnicaltest

import android.app.Application

class FritechnicaltestApplication : Application() {

    private var instance: FritechnicaltestApplication? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    @Synchronized
    fun getInstance(): FritechnicaltestApplication? {
        return instance
    }
}