package com.spacey.codedatabase.question

import com.spacey.codedatabase.BaseRepository
import kotlinx.coroutines.withContext

class QuestionsRepository(private val apiService: QuestionsApiService) : BaseRepository() {
    suspend fun getQuestions(): Result<List<Question>> {
        return withContext(defaultContext) {
            apiService.getAllQuestions()
        }
    }
}