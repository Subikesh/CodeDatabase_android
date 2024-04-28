package com.spacey.codedatabase.android.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    var greetingText by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    var query by remember {
        mutableStateOf("")
    }
    var searchActive by remember {
        mutableStateOf(false)
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
                DockedSearchBar(query = query, modifier = Modifier.fillMaxWidth(), onQueryChange = {
                    query = it
                }, onSearch = {
                    /*TODO*/
                    searchActive = false
                }, active = searchActive, onActiveChange = {
                    searchActive = it
                }, leadingIcon = {
                    AnimatedContent(targetState = searchActive, label = "Search and back button") { active ->
                        if (active) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Search cancel",
                                modifier = Modifier.clickable {
                                    searchActive = false
                                    query = ""
                                })
                        } else {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                }, placeholder = {
                    Text(text = "Search Questions")
                }) {
                    LazyColumn {
                        if (uiState.isSearchLoading) {
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    CircularProgressIndicator()
                                }
                            }
                        } else {
                            items(uiState.searchResults) { question ->
                                Text(text = question.title, modifier = Modifier.padding(top = 8.dp))
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }

            item {
                greetingText = uiState.questionsList.fold(
                    onSuccess = {
                        "Success: $it"
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        exception.stackTraceToString()
                    }
                )
                Text(text = greetingText, modifier = Modifier.padding(vertical = 16.dp))
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