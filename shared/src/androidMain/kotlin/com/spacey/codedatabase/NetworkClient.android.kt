package com.spacey.codedatabase

import io.ktor.client.HttpClient
import io.ktor.client.engine.config
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.Logging

actual val ktorHttpClient: HttpClient = HttpClient(OkHttp.config {
    config {
        hostnameVerifier { _, _ -> true }
    }
}) {
    install(Logging)
}