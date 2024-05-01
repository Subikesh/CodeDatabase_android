package com.spacey.codedatabase.android.component

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spacey.codedatabase.android.R
import com.spacey.codedatabase.question.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeDbSearchBar(query: String, isSearchLoading: Boolean, searchResults: List<Question>, onQueryChange: (String) -> Unit) {
    var searchActive by remember {
        mutableStateOf(false)
    }

    DockedSearchBar(query = query, modifier = Modifier.fillMaxWidth(), onQueryChange = onQueryChange, onSearch = {
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
                        onQueryChange("")
                    })
            } else {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    }, trailingIcon = {
        Icon(
            painter = painterResource(id = R.drawable.filter_list_24),
            contentDescription = "Filter list",
            modifier = Modifier.clickable { /* TODO */ })
    }, placeholder = {
        Text(text = "Search Questions")
    }) {
        LazyColumn {
            if (isSearchLoading) {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                }
            } else {
                items(searchResults) { question ->
                    Text(text = question.title, modifier = Modifier.padding(top = 8.dp))
                    HorizontalDivider()
                }
            }
        }
    }
}