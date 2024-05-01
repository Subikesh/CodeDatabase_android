package com.spacey.codedatabase.question

import com.spacey.codedatabase.NetworkService
import com.spacey.codedatabase.Settings
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class QuestionsApiService(settings: Settings) : NetworkService(settings) {
    suspend fun getAllQuestions(): Result<List<Question>> = serializedRequest("questions")
}