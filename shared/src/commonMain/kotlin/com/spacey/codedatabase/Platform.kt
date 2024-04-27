package com.spacey.codedatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform