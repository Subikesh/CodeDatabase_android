package com.spacey.codedatabase

import com.spacey.codedatabase.question.QuestionsApiService
import com.spacey.codedatabase.question.QuestionsRepository

object AppComponent {

    private val questionApi: QuestionsApiService by lazy { QuestionsApiService() }
    val questionRepo: QuestionsRepository by lazy { QuestionsRepository(questionApi) }

//    fun initiate() {
//
//    }
}