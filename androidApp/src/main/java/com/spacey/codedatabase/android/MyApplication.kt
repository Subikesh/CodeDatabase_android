package com.spacey.codedatabase.android

import android.app.Application
import com.spacey.codedatabase.initiateSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initiateSdk(this)
    }
}