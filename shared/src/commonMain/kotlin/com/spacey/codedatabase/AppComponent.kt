package com.spacey.codedatabase

import com.spacey.codedatabase.question.QuestionsApiService
import com.spacey.codedatabase.question.QuestionsCache
import com.spacey.codedatabase.question.QuestionsRepository

object AppComponent {

    private val questionApi: QuestionsApiService by lazy { QuestionsApiService() }
    private val questionsCache: QuestionsCache by lazy { QuestionsCache() }

    val questionRepo: QuestionsRepository by lazy { QuestionsRepository(questionApi, questionsCache) }

//    fun initiate() {
//
//    }
}