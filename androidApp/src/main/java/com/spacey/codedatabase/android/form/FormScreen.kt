package com.spacey.codedatabase.android.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacey.codedatabase.android.FabConfig
import com.spacey.codedatabase.question.Difficulty
import com.spacey.codedatabase.question.Question

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(preFill: Question?, setFabConfig: (FabConfig?) -> Unit) {
    var title by remember {
        mutableStateOf(preFill?.title ?: "")
    }
    var link by remember {
        mutableStateOf(preFill?.link ?: "")
    }
    var description by remember {
        mutableStateOf(preFill?.description ?: "")
    }
    var difficulty by remember {
        mutableStateOf(preFill?.difficulty)
    }
    val tagList = remember {
        mutableStateListOf(*(preFill?.tag?.toTypedArray() ?: emptyArray()))
    }
    var examples by remember {
        mutableStateOf(preFill?.examples ?: "")
    }


    var tagDropdownExpanded by remember {
        mutableStateOf(false)
    }
    var difficultyDropdownExpanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        setFabConfig(FabConfig.SubmitForm {
            // TODO: On submit handling
        })

    }

    Column {
        TopAppBar(title = { Text(text = "${if (preFill != null) "Edit" else "Add"} Question") })

        TextField(value = title, onValueChange = { title = it }, label = { Text(text = "Title") }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp))

        TextField(
            value = link,
            onValueChange = { link = it },
            label = { Text(text = "Link") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
        )

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            minLines = 3,
            singleLine = false
        )

        ExposedDropdownMenuBox(
            expanded = difficultyDropdownExpanded, onExpandedChange = { difficultyDropdownExpanded = it }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = difficulty?.name ?: "",
                placeholder = { Text("Difficulty") },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = difficultyDropdownExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = difficultyDropdownExpanded,
                onDismissRequest = { difficultyDropdownExpanded = false }
            ) {
                for (diffItem in Difficulty.entries) {
                    val color = if (diffItem == difficulty) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else Color.Transparent
                    DropdownMenuItem(text = { Text(diffItem.name) }, modifier = Modifier.fillMaxWidth().background(color), onClick = {
                        difficulty = diffItem
                        difficultyDropdownExpanded = false
                    })
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = tagDropdownExpanded,
            onExpandedChange = { tagDropdownExpanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TextField(
                value = tagList.sorted().joinToString(),
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(text = "Tag") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tagDropdownExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(expanded = tagDropdownExpanded, onDismissRequest = { tagDropdownExpanded = false }) {
                for (tag in 0..10) {
                    val color = if (tagList.contains(tag))
                        MaterialTheme.colorScheme.primaryContainer
                    else Color.Transparent
                    DropdownMenuItem(
                        text = { Text("Tag $tag") },
                        modifier = Modifier.background(color),
                        onClick = {
                            if (tagList.contains(tag)) {
                                tagList.remove(tag)
                            } else {
                                tagList.add(tag)
                            }
                        })
                }
            }
        }

        TextField(
            value = examples,
            onValueChange = { examples = it },
            label = { Text(text = "Examples") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            minLines = 3,
            singleLine = false
        )
    }
}

@Preview
@Composable
fun Preview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    )
    FormScreen(preFill = null) {

    }
}