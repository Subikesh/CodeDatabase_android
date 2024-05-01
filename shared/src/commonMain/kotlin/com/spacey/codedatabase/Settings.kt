package com.spacey.codedatabase

expect class SettingsService {
    fun getString(key: String): String?
    fun setString(key: String, value: String)
    fun remove(key: String)
}

class Settings(private val service: SettingsService) {
    var authToken: String?
        get() = service.getString(AUTH_TOKEN)
        set(value) {
            if (value.isNullOrEmpty()) {
                service.remove(AUTH_TOKEN)
            } else {
                service.setString(AUTH_TOKEN, value)
            }
        }

    companion object {
        const val AUTH_TOKEN = "auth_token"
    }
}

