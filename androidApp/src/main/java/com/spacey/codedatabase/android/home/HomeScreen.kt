package com.spacey.codedatabase.android.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spacey.codedatabase.android.component.CdSearchBar

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    var greetingText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    var query by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(HomeEvent.Initialise)
    }
    LaunchedEffect(key1 = query) {
        viewModel.onEvent(HomeEvent.Search(query))
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            item {
                CdSearchBar(
                    query = query,
                    isSearchLoading = uiState.isSearchLoading,
                    searchResults = uiState.searchResults
                ) {
                    query = it
                }
            }

            if (uiState.questionsList.isSuccess) {
                items(uiState.questionsList.getOrThrow()) { question ->
                    QuestionCard(question = question, modifier = Modifier.padding(top = 16.dp))
                }
            } else {
                item(Modifier.fillMaxSize()) {
                    Column {
                        Text(
                            text = "Something went wrong when fetching questions",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}