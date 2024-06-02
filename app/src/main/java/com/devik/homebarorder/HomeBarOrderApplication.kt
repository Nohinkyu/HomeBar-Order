package com.devik.homebarorder

import android.app.Application
import com.devik.homebarorder.data.source.local.PreferenceManager

class HomeBarOrderApplication : Application() {

    companion object {
        lateinit var prefsManager: PreferenceManager
    }

    override fun onCreate() {
        super.onCreate()
        prefsManager = PreferenceManager(this)
    }
}