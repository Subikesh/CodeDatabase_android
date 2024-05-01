package com.spacey.codedatabase.auth

import com.spacey.codedatabase.Settings
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class AuthRepository(private val settings: Settings, private val authApiService: AuthApiService) {

    suspend fun authenticate(username: String, password: String): String? {
        try {
            val authToken = getAuthToken()
            if (authToken == null) {
                val response = authApiService.authenticate(username, password)
                if (response.status.isSuccess()) {
                    val res = response.body<AuthResponse>()
                    settings.authToken = res.token
                    return res.token
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getAuthToken() = settings.authToken

    fun logout() {
        settings.authToken = null
    }
}