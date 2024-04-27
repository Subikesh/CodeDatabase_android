package com.spacey.codedatabase

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse

open class NetworkService(private val httpClient: HttpClient = ktorHttpClient) {

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

expect val ktorHttpClient: HttpClient

