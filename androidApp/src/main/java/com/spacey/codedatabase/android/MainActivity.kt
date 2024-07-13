package com.spacey.codedatabase.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spacey.codedatabase.android.auth.AuthViewModel
import com.spacey.codedatabase.android.auth.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val authViewModel: AuthViewModel = viewModel()
                    val startDestination = if (authViewModel.isAuthenticated()) TopLevelDestination.CODE_DB.route.getRoute(true.toString()) else TopLevelDestination.LOGIN.route
                    NavHost(navController = navController, startDestination = startDestination) {
                        composable(TopLevelDestination.LOGIN.route) {
                            LoginScreen(navController, authViewModel)
                        }
                        composable(
                            TopLevelDestination.CODE_DB.route,
                            arguments = listOf(
                                navArgument("is_logged_in") { type = NavType.BoolType }
                            )
                        ) {
                            HomeNavigation(it.arguments?.getBoolean("is_logged_in") == true, navigateToLogin = {
                                navController.navigateTopLevel(TopLevelDestination.LOGIN.route)
                            })
                        }
                    }
                }
            }
        }
    }
}

enum class TopLevelDestination(val route: String) {
    LOGIN("login"), CODE_DB("code_database/{is_logged_in}")
}