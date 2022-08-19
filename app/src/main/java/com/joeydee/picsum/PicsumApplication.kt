package com.joeydee.picsum

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PicsumApplication : Application() {

    companion object {
        lateinit var instance: PicsumApplication private set
        val context: Context
            get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}