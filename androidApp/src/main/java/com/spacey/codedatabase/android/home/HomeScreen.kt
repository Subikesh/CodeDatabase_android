package com.spacey.codedatabase.android.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.spacey.codedatabase.android.component.CodeDbSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    var query by remember {
        mutableStateOf("")
    }
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(HomeEvent.Initialise)
    }
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(key1 = true) {
            viewModel.onEvent(HomeEvent.PullToRefresh)
        }
    }
    if (uiState.loadingState == HomeLoadingState.INITIAL || uiState.loadingState == HomeLoadingState.SEARCH) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        if (uiState.loadingState == HomeLoadingState.REFRESH) {
            LaunchedEffect(key1 = true) {
                pullToRefreshState.startRefresh()
            }
        } else {
            LaunchedEffect(key1 = true) {
                pullToRefreshState.endRefresh()
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)) {

            CodeDbSearchBar(
                query = query,
                isSearchLoading = uiState.loadingState == HomeLoadingState.SEARCH,
                searchResults = uiState.searchResults
            ) {
                query = it
                viewModel.onEvent(HomeEvent.Search(it))
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                LazyColumn {
                    if (uiState.questionsList.isSuccess) {
                        items(uiState.questionsList.getOrThrow()) { question ->
                            QuestionCard(question = question, modifier = Modifier.padding(vertical = 8.dp))
                        }
                    } else {
                        item {
                            Text(
                                text = "Something went wrong when fetching questions",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
                PullToRefreshContainer(state = pullToRefreshState, modifier = Modifier.align(Alignment.TopCenter))
            }
        }
    }
}