package com.spacey.codedatabase.android.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<UiState, Event> : ViewModel() {
    protected abstract val _uiState: MutableStateFlow<UiState>
    val uiState: StateFlow<UiState> = _uiState

    abstract fun onEvent(event: Event)
}