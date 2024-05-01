package com.spacey.codedatabase.android.auth

import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import com.spacey.codedatabase.android.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : BaseViewModel<AuthUiState, AuthEvent>() {

    override val _uiState = MutableStateFlow(AuthUiState())

    private val authRepository = AppComponent.instance.authRepository

    override fun onEvent(event: AuthEvent) {
        viewModelScope.launch {
            var loginError: String? = null
            val authToken: String? = when (event) {
                is AuthEvent.Initiate -> {
                    authRepository.getAuthToken()
                }

                is AuthEvent.Login -> {
                    _uiState.value = uiState.value.copy(isLoading = true, loginError = null)
                    val token = authRepository.authenticate(event.userName, event.password)
                    if (token == null) {
                        loginError = "Your credentials are invalid. Please check your username or password and try again!"
                    }
                    token
                }
            }
            _uiState.value = uiState.value.copy(isLoading = false, isAuthenticated = !authToken.isNullOrEmpty(), loginError = loginError)
        }
    }

    fun isAuthenticated() = !authRepository.getAuthToken().isNullOrEmpty()
}

sealed class AuthEvent {
    data object Initiate : AuthEvent()
    data class Login(val userName: String, val password: String) : AuthEvent()
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val loginError: String? = null
)