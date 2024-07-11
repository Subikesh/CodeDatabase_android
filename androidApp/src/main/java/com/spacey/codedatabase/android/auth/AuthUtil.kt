package com.spacey.codedatabase.android.auth

import com.spacey.codedatabase.AppComponent

object AuthUtil {
    suspend fun isUserAuthenticated(): Boolean {
        val repository = AppComponent.instance.authRepository
        return if (repository.getCachedUser() != null) true else repository.getCurrentUser().isSuccess
    }
}