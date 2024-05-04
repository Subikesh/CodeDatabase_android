package com.spacey.codedatabase.android.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun UserScreen(isLoggedIn: Boolean, userViewModel: UserViewModel, navigateToLogin: () -> Unit) {
    val uiState by userViewModel.uiState.collectAsState()
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        userViewModel.onEvent(UserEvent.Initiate(isLoggedIn))
    }

    val currentUser = uiState.currentUser
    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {
        if (currentUser != null) {
            Column(Modifier.padding(top = 40.dp, start = 16.dp, end = 16.dp)) {
                Text(text = "Hi, ", style = MaterialTheme.typography.titleLarge)
                Text(text = currentUser.fullName, style = MaterialTheme.typography.displayLarge)
                Text(text = currentUser.email, style = MaterialTheme.typography.titleMedium)
            }
        } else {
            Text(text = "No user is logged in.", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
        }

        IconButton(onClick = {
            showLogoutDialog = true
        }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
        }
    }

    if (showLogoutDialog) {
        if (uiState.currentUser != null) {
            AlertDialog(onDismissRequest = {
                showLogoutDialog = false
            }, confirmButton = {
                Button(onClick = {
                    userViewModel.onEvent(UserEvent.Logout)
                    navigateToLogin()
                }) {
                    Text(text = "Yes, Logout!")
                }
            }, text = {
                Text(text = "Are you sure you want to logout?")
            })
        } else {
            navigateToLogin()
        }
    }

}