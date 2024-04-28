package com.spacey.codedatabase.android.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.spacey.codedatabase.question.Question

@Composable
fun QuestionCard(question: Question, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val intent = remember {
        Intent(Intent.ACTION_VIEW, Uri.parse(question.link))
    }
    Card(modifier = modifier
        .fillMaxWidth(), shape = RoundedCornerShape(30.dp)) {
        Column(verticalArrangement = Arrangement.SpaceAround) {
            Row {
                Text(
                    text = question.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                if (question.link != null) {
                    IconButton(onClick = { context.startActivity(intent) }) {
                        Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "Open Link")
                    }
                }
            }
            Text(
                text = question.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp),
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}