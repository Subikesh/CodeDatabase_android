package com.spacey.codedatabase.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val repository by lazy { AppComponent.questionRepo }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Initialise -> {
                _uiState.value = HomeUiState.Loading
                viewModelScope.launch {
                    delay(1000)
                    val res = repository.getQuestions()
                    if (res.isSuccess) {
                        _uiState.value = HomeUiState.Success("Success: ${res.getOrThrow()}")
                    } else {
                        val exception = res.exceptionOrNull()
                        exception?.printStackTrace()
                        _uiState.value = HomeUiState.Error(exception!!)
                    }
                }

            }
        }
    }
}

sealed class HomeEvent {
    data object Initialise : HomeEvent()
}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val data: String) : HomeUiState()
    data class Error(val exception: Throwable) : HomeUiState()
}