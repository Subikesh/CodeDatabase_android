package com.spacey.codedatabase.question

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val title: String,
    val access: Access,
    val description: String,
    val link: String?,
    val difficulty: Difficulty,
    val tag: List<Int>,
    val examples: String,
    val solutions: List<Solution>,
    @SerialName("date_added") val dateAdded: Instant?
)

@Serializable
data class Solution(
    val title: String,
    val language: String,
    val program: String,
    val notes: String,
    val link: String,
    @SerialName("date_added") val dateAdded: Instant
)

enum class Difficulty {
    Easy, Medium, Hard
}

enum class Access {
    Public, Private
}