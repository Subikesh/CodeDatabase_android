package com.spacey.codedatabase

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val ktorHttpClient: HttpClient = HttpClient(Darwin)