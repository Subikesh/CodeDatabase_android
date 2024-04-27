package com.spacey.codedatabase

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

open class NetworkService(private val httpClient: HttpClient = HttpClient(httpClientEngine) {
    install(Logging)
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}) {

    protected suspend fun request(endpoint: String, block: HttpRequestBuilder.() -> Unit = {}): HttpResponse {
        return httpClient.request {
            url("${BASE_URL}$endpoint")
            getAuthToken()?.let {
                headers["Authorization"] = it
            }
            block()
        }
    }

    // TODO: Get auth from user
    private fun getAuthToken(): String? = null

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8000/api/"
    }
}

expect val httpClientEngine: HttpClientEngine

