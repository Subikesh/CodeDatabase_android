package com.spacey.codedatabase.android.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val authRepository = AppComponent.instance.authRepository

    fun onEvent(event: AuthEvent) {
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

                is AuthEvent.Logout -> {
                    authRepository.logout()
                    null
                }
            }
            _uiState.value = uiState.value.copy(isLoading = false, isAuthenticated = !authToken.isNullOrEmpty(), loginError = loginError)
        }
    }
}

sealed class AuthEvent {
    data object Initiate : AuthEvent()
    data class Login(val userName: String, val password: String) : AuthEvent()
    data object Logout : AuthEvent()
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val loginError: String? = null
)