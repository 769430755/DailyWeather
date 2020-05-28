package com.dailyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class MyApplication : Application() {

    companion object {
        // TO-DO
        const val TOKEN = "Z0H3w9DQ2mTcmPCW"

        lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}