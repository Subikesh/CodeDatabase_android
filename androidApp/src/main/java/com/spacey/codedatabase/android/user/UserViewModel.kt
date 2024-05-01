package com.spacey.codedatabase.android.user

import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import com.spacey.codedatabase.android.auth.AuthEvent
import com.spacey.codedatabase.android.base.BaseViewModel
import com.spacey.codedatabase.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : BaseViewModel<UserUiState, UserEvent>() {
    override val _uiState = MutableStateFlow(UserUiState())

    private val authRepository = AppComponent.instance.authRepository

    override fun onEvent(event: UserEvent) {
        when (event) {
            UserEvent.Initiate -> {
                _uiState.value = uiState.value.copy(isLoading = true)
                viewModelScope.launch {
                    _uiState.value = uiState.value.copy(isLoading = false, currentUser = authRepository.getCurrentUser().getOrNull())
                }
            }

            is UserEvent.Logout -> {
                authRepository.logout()
            }
        }
    }
}

sealed class UserEvent {
    data object Initiate : UserEvent()
    data object Logout : UserEvent()
}

data class UserUiState(
    val isLoading: Boolean = false,
    val currentUser: User? = null
)