package com.example.pokmonpictorialbook.ui

import android.app.Application
import com.example.pokmonpictorialbook.di.AppContainer

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(applicationContext)
    }
}