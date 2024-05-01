package com.spacey.codedatabase.auth

import com.spacey.codedatabase.NetworkService
import com.spacey.codedatabase.Settings
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class AuthApiService(settings: Settings) : NetworkService(settings) {
    suspend fun authenticate(username: String, password: String): HttpResponse {
        return request("auth-token") {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(mapOf(
                "username" to username,
                "password" to password
            ))
        }
    }

    suspend fun getCurrentUser(): Result<User> = serializedRequest("user")
}

@Serializable
data class AuthResponse(val token: String)