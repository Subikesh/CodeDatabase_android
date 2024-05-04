package com.spacey.codedatabase.android.auth

import com.spacey.codedatabase.AppComponent

object AuthUtil {
    fun isUserAuthenticated(): Boolean {
        val repository = AppComponent.instance.authRepository
        return repository.getCachedUser() != null
    }
}