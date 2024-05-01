package com.spacey.codedatabase

import android.content.Context

fun initiateSdk(context: Context) {
    AppComponent.instance.settingsService = SettingsService(context)
}
