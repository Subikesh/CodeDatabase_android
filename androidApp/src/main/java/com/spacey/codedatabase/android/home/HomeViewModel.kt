package com.spacey.codedatabase.android.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import com.spacey.codedatabase.question.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState

    private val repository by lazy { AppComponent.questionRepo }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Initialise -> {
                _uiState.value = uiState.value.copy(isLoading = true)
                viewModelScope.launch {
                    delay(1000) // TODO: remove delay
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        questionsList = repository.getQuestions()
                    )
                }
            }
            is HomeEvent.Search -> {
                _uiState.value = uiState.value.copy(isSearchLoading = true)
                viewModelScope.launch {
                    _uiState.value = uiState.value.copy(
                        isSearchLoading = false,
                        searchResults = repository.filterQuestions(event.query).getOrDefault(emptyList())
                    )
                }
            }
        }
    }
}

sealed class HomeEvent {
    data object Initialise : HomeEvent()
    data class Search(val query: String) : HomeEvent()
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val questionsList: Result<List<Question>> = Result.success(emptyList()),
    val isSearchLoading: Boolean = false,
    val searchResults: List<Question> = emptyList()
)