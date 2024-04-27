package com.spacey.codedatabase

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.config
import io.ktor.client.engine.okhttp.OkHttp

actual val httpClientEngine: HttpClientEngine = OkHttp.config {
    config {
        hostnameVerifier { _, _ -> true }
    }
}.create()