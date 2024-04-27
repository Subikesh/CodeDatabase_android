package com.spacey.codedatabase.question

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val title: String,
    val access: String,
    val description: String,
    val link: String,
    val difficulty: String,
    val tag: List<String>,
    val examples: String,
    val solutions: List<Solution>
)

@Serializable
data class Solution(
    val title: String,
    val language: String,
    val program: String,
    val notes: String,
    val link: String,
//    val dateAdded: DateTime
)