package com.spacey.codedatabase.android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.spacey.codedatabase.android.home.HomeScreen

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route ?: Destination.HOME.route


    Scaffold(bottomBar = {
        NavigationBar {
            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                NavigationBarItem(
                    selected = currentRoute == destination.route,
                    onClick = {
                        navController.navigateTopLevel(destination.route)
                    },
                    icon = { Icon(imageVector = destination.icon, contentDescription = destination.iconText) }
                )
            }
        }
    }) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = currentRoute) {
                composable(Destination.HOME.route) {
                    HomeScreen(navController = navController)
                }
                composable(Destination.ACCOUNT.route) {
                    EmptyComingSoon()
                }
            }
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    Destination.HOME,
    Destination.ACCOUNT
)

sealed class Destination(
    val route: String,
    val icon: ImageVector,
    val iconText: String
) {
    data object HOME : Destination("home", Icons.Default.Home, "Home")
    data object ACCOUNT : Destination("account", Icons.Default.AccountCircle, "Account")
}

fun NavHostController.navigateTopLevel(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateTopLevel.graph.findStartDestination().id)
        launchSingleTop = true
    }
}

@Preview
@Composable
fun PreviewHome() {
    HomeNavigation()
}