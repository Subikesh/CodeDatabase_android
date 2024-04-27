package com.spacey.codedatabase.question

import com.spacey.codedatabase.NetworkService
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class QuestionsApiService : NetworkService() {
    suspend fun getAllQuestions(): Result<String> {
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