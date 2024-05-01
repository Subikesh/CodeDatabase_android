package com.spacey.codedatabase.question

import com.spacey.codedatabase.NetworkService
import com.spacey.codedatabase.Settings
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class QuestionsApiService(settings: Settings) : NetworkService(settings) {
    suspend fun getAllQuestions(): Result<List<Question>> {
        return try {
            val response = request("questions")
            if (response.status.isSuccess()) {
                Result.success(response.body())
            } else {
                Result.failure(Exception("API response failure: ${response.body<String>()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}