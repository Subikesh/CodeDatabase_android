package com.spacey.codedatabase.android.home

import androidx.lifecycle.viewModelScope
import com.spacey.codedatabase.AppComponent
import com.spacey.codedatabase.android.base.BaseViewModel
import com.spacey.codedatabase.question.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel<HomeUiState, HomeEvent>() {

    override val _uiState = MutableStateFlow(HomeUiState())

    private val repository by lazy { AppComponent.instance.questionRepo }

    override fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Initialise -> {
                refreshQuestionList()
                _uiState.value = uiState.value.copy(loadingState = LoadingState.INITIAL)
            }
            is HomeEvent.PullToRefresh -> {
                refreshQuestionList()
                _uiState.value = uiState.value.copy(loadingState = LoadingState.REFRESH)
            }
            is HomeEvent.Search -> {
                if (event.query.isBlank()) {
                    _uiState.value = uiState.value.copy(loadingState = LoadingState.NONE, )
                }
                _uiState.value = uiState.value.copy(loadingState = LoadingState.SEARCH)
                viewModelScope.launch {
                    _uiState.value = uiState.value.copy(
                        loadingState = LoadingState.NONE,
                        searchResults = repository.filterQuestions(event.query).getOrDefault(emptyList())
                    )
                }
            }
        }
    }

    private fun refreshQuestionList() {
        viewModelScope.launch {
            delay(1000) // TODO: remove delay
            _uiState.value = uiState.value.copy(
                loadingState = LoadingState.NONE,
                questionsList = repository.getQuestions()
            )
        }
    }
}

sealed class HomeEvent {
    data object Initialise : HomeEvent()
    data object PullToRefresh : HomeEvent()
    data class Search(val query: String) : HomeEvent()
}

enum class LoadingState {
    INITIAL, REFRESH, SEARCH, NONE
}

data class HomeUiState(
    val loadingState: LoadingState = LoadingState.NONE,
    val questionsList: Result<List<Question>> = Result.success(emptyList()),
    val searchResults: List<Question> = emptyList()
)