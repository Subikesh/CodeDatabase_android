package com.spacey.codedatabase.android

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    var fabState: FabState by remember {
        mutableStateOf(FabState.ADD)
    }

    var submitClicked by remember {
        mutableStateOf(false)
    }

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
    }, floatingActionButton = {
        LargeFloatingActionButton(
            shape = RoundedCornerShape(20.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            onClick = {
                if (fabState == FabState.SUBMIT) {
                    submitClicked = true
                }
                navController.navigate(fabState.destination.route)
            }) {
            AnimatedContent(targetState = fabState, label = "Home FAB") { fabState ->
                Icon(
                    imageVector = fabState.destination.icon,
                    contentDescription = fabState.destination.iconText,
                    Modifier.size(28.dp)
                )
            }
        }
    }) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = currentRoute) {
                composable(Destination.HOME.route) {
                    fabState = FabState.ADD
                    HomeScreen()
                }
                composable(Destination.ACCOUNT.route) {
                    fabState = FabState.EDIT
                    EmptyComingSoon()
                }
                composable(Destination.NEW_QUESTION.route) {
                    fabState = FabState.SUBMIT
                    EmptyComingSoon()
                }
                composable(Destination.EDIT_QUESTION.route) {
                    fabState = FabState.SUBMIT
                    EmptyComingSoon()
                }
            }
        }
    }
}

enum class FabState(val destination: Destination) {
    ADD(Destination.NEW_QUESTION), EDIT(Destination.EDIT_QUESTION), SUBMIT(Destination.SUBMIT)
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
    data object NEW_QUESTION : Destination("add_question", Icons.Default.Add, "New Question")
    data object EDIT_QUESTION : Destination("edit_question/{question_id}", Icons.Default.Edit, "Edit question")
    data object SUBMIT : Destination("home", Icons.Default.Done, "Submit form")
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