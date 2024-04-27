package com.spacey.codedatabase.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacey.codedatabase.AppComponent
import com.spacey.codedatabase.question.QuestionsRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(AppComponent.questionRepo)
                }
            }
        }
    }
}

@Composable
fun GreetingView(repo: QuestionsRepository) {
    var greetingText by remember { mutableStateOf("Loading...") }
    var refresh by remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = refresh) {
        if (refresh) {
            greetingText = "Loading..."
            val res = repo.getQuestions()
            greetingText = if (res.isSuccess) {
                "Success: ${res.getOrThrow()}"
            } else {
                val exception = res.exceptionOrNull()
                exception?.printStackTrace()
                "Failure: $exception"
            }
            refresh = false
        }
    }
    Column {
        Button({
            refresh = true
        }) {
            Text("Reload")
        }
        Text(text = greetingText)
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView(AppComponent.questionRepo)
    }
}
