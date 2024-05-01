package com.spacey.codedatabase

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

open class NetworkService(
    private val settings: Settings,
    private val httpClient: HttpClient = HttpClient(httpClientEngine) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
) {

    protected suspend fun request(endpoint: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
        return httpClient.request {
            url("${BASE_URL}$endpoint/")
            getAuthToken()?.let {
                headers["Authorization"] = it
            }
            block()
        }
    }

    protected suspend inline fun <reified Entity> serializedRequest(endpoint: String): Result<Entity> {
        return try {
            val response = request(endpoint = endpoint)
            if (response.status.isSuccess()) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("API response failure: ${response.body<String>()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getAuthToken(): String? = settings.authToken?.let { "Token $it" }

    companion object {
        private const val BASE_URL = "http://codedatabase.pythonanywhere.com/api/"
    }
}

expect val httpClientEngine: HttpClientEngine

