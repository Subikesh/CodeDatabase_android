package com.spacey.codedatabase.android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spacey.codedatabase.android.home.HomeScreen

@Composable
fun CdNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.homeRoute()) {
        composable(NavRoute.homeRoute()) {
            HomeScreen(navController = navController)
        }
    }
}

object NavRoute {
    fun homeRoute() = "home"
}