package com.spacey.codedatabase.android.user

import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import com.spacey.codedatabase.android.base.BaseViewModel
import com.spacey.codedatabase.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel : BaseViewModel<UserUiState, UserEvent>() {
    private val authRepository = AppComponent.instance.authRepository

    override val _uiState = MutableStateFlow(UserUiState(authRepository.getCachedUser()))

    override fun onEvent(event: UserEvent) {
        when (event) {
            is UserEvent.Initiate -> {
                viewModelScope.launch {
                    if (event.isLoggedIn) {
                        _uiState.value = uiState.value.copy(currentUser = authRepository.getCachedUser())
                    }
                    _uiState.value = uiState.value.copy(currentUser = authRepository.getCurrentUser().getOrNull())
                }
            }

            is UserEvent.Logout -> {
                authRepository.logout()
            }
        }
    }
}

sealed class UserEvent {
    data class Initiate(val isLoggedIn: Boolean) : UserEvent()
    data object Logout : UserEvent()
}

data class UserUiState(
    val currentUser: User?
)