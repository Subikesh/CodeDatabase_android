package com.spacey.codedatabase.question

import com.spacey.codedatabase.BaseRepository
import kotlinx.coroutines.withContext

class QuestionsRepository(private val apiService: QuestionsApiService, private val questionsCache: QuestionsCache) : BaseRepository() {
    suspend fun getQuestions(): Result<List<Question>> {
        return withContext(defaultContext) {
            apiService.getAllQuestions().also {
                questionsCache.questions = it.getOrNull()
            }
        }
    }

    /**
     * Filter questions from the stored cache. If cache is empty, refresh the list
     */
    suspend fun filterQuestions(query: String): Result<List<Question>> {
        val cache = questionsCache.questions
        if (!cache.isNullOrEmpty()) {
            return Result.success(if (query.isNotBlank()) cache.search(query) else cache)
        }
        return getQuestions().map { it.search(query) }
    }

    private fun List<Question>.search(query: String): List<Question> =
        this.filter { question ->
            question.title.split("\\s+".toRegex()).any { it.startsWith(query, ignoreCase = true) }
//                    || question.description.startsWith(query, ignoreCase = true)
        }
}