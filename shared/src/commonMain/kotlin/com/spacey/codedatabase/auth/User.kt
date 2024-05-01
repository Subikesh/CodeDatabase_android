package com.spacey.codedatabase.auth

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username: String,
    val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("date_joined") val dateJoined: Instant
) {
    val fullName: String
        get() = "$firstName $lastName"
}