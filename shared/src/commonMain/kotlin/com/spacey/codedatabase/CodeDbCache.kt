package com.spacey.codedatabase

import com.spacey.codedatabase.auth.User
import com.spacey.codedatabase.question.Question

class CodeDbCache {
    var currentUser: User? = null
    var questions: List<Question>? = null

    fun clear() {
        currentUser = null
        questions = null
    }
}