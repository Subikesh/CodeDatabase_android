package com.spacey.codedatabase.auth

import com.spacey.codedatabase.BaseRepository
import com.spacey.codedatabase.CodeDbCache
import com.spacey.codedatabase.Settings
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.withContext

class AuthRepository(private val settings: Settings, private val authApiService: AuthApiService, private val codeDbCache: CodeDbCache) : BaseRepository() {

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
                /* TODO: Error handling if it was bad credentials */
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    suspend fun getCurrentUser(): Result<User> {
        return try {
            val cachedUser = codeDbCache.currentUser
            if (cachedUser == null) {
                withContext(defaultContext) {
                    authApiService.getCurrentUser().map {
                        codeDbCache.currentUser = it
                        it
                    }
                }
            } else {
                Result.success(cachedUser)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAuthToken() = settings.authToken

    fun logout() {
        settings.authToken = null
        codeDbCache.clear()
    }
}