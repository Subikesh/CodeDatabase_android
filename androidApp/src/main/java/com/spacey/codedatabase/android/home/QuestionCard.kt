package com.spacey.codedatabase.android.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spacey.codedatabase.question.Question

@Composable
fun QuestionCard(question: Question, modifier: Modifier = Modifier) {
    Card(modifier = modifier.height(500.dp).fillMaxWidth(), shape = RoundedCornerShape(30.dp)) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Text(
                text = question.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = question.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}