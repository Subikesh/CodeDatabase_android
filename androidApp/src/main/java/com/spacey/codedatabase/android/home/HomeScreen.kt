package com.spacey.codedatabase.android.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: HomeViewModel = viewModel()) {
    var greetingText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(HomeEvent.Initialise)
    }
    when (val state = uiState) {
        HomeUiState.Loading -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        is HomeUiState.Success -> {
            greetingText = state.data
        }
        is HomeUiState.Error -> {
            greetingText = state.exception.stackTraceToString()
        }
    }
    if (uiState !is HomeUiState.Loading) {
        LazyColumn(modifier) {
            item {
                Text(text = greetingText)
            }
            item {
                Button({
                    viewModel.onEvent(HomeEvent.Initialise)
                }) {
                    Text("Reload")
                }
            }
        }
    }
}