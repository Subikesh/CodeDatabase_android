package com.spacey.codedatabase

import com.spacey.codedatabase.auth.AuthApiService
import com.spacey.codedatabase.auth.AuthRepository
import com.spacey.codedatabase.question.QuestionsApiService
import com.spacey.codedatabase.question.QuestionsCache
import com.spacey.codedatabase.question.QuestionsRepository

class AppComponent private constructor(){

    private val authApiService: AuthApiService by lazy { AuthApiService(settings) }
    private val questionApi: QuestionsApiService by lazy { QuestionsApiService(settings) }

    private val questionsCache: QuestionsCache by lazy { QuestionsCache() }

    internal lateinit var settingsService: SettingsService
    private val settings: Settings by lazy { Settings(settingsService) }

    /* Repositories */
    val questionRepo: QuestionsRepository by lazy { QuestionsRepository(questionApi, questionsCache) }
    val authRepository: AuthRepository by lazy { AuthRepository(settings, authApiService) }

    companion object {
        val instance = AppComponent()
    }
}