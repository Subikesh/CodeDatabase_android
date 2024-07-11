package com.spacey.codedatabase.android.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.spacey.codedatabase.android.TopLevelDestination
import com.spacey.codedatabase.android.getRoute
import com.spacey.codedatabase.android.navigateTopLevel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPassVisible by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val uiState by authViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        authViewModel.onEvent(AuthEvent.Initiate)
    }
    if (uiState.isAuthenticated) {
        LaunchedEffect(key1 = true) {
            navController.navigateTopLevel(TopLevelDestination.CODE_DB.route.getRoute(true.toString()))
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.CenterHorizontally)
            )
            TextField(label = { Text("UserName") }, value = userName, onValueChange = {
                userName = it
            }, leadingIcon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Username")
            }, modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp))

            TextField(
                value = password,
                label = { Text("Password") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { isPassVisible = !isPassVisible }) {
                        Icon(
                            imageVector = if (isPassVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Password visibility"
                        )
                    }

                },
                onValueChange = {
                    password = it
                },
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )

            Row(
                Modifier
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = { authViewModel.onEvent(AuthEvent.Login(userName, password)) },
                    enabled = !uiState.isLoading
                ) {
                    Text(text = "Login")
                }

                Button(
                    modifier = Modifier
                        .padding(start = 12.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Register")
                }

            }

            Text(
                text = "Sample credentials for login. \n * Username: jimhalpert\n * Password: JimHalpert123",
                modifier = Modifier.padding(top = 12.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
            )
        }
        TextButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            enabled = !uiState.isLoading,
            onClick = {
                navController.navigateTopLevel(TopLevelDestination.CODE_DB.route.getRoute(false.toString()))
            }) {
            Text("Continue without login >")
        }
        if (uiState.isLoading) {
            Box(modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)))

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Text(text = "Logging in...")
            }
        }

        val error = uiState.loginError
        if (error != null) {
            password = ""
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }
}