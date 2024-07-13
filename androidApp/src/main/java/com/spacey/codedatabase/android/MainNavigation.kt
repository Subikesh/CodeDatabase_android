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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spacey.codedatabase.android.auth.AuthUtil
import com.spacey.codedatabase.android.form.FormScreen
import com.spacey.codedatabase.android.home.HomeScreen
import com.spacey.codedatabase.android.home.HomeViewModel
import com.spacey.codedatabase.android.user.UserScreen
import com.spacey.codedatabase.android.user.UserViewModel

@Composable
fun HomeNavigation(
    isUserLoggedInFromLogin: Boolean,
    homeViewModel: HomeViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    navigateToLogin: () -> Unit
) {
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route ?: Destination.HOME.route

    val (fabConfig, setFabConfig) = remember {
        mutableStateOf<FabConfig?>(null)
    }

    var isUserAuthenticated by remember {
        mutableStateOf(isUserLoggedInFromLogin)
    }

    LaunchedEffect(true) {
        isUserAuthenticated = AuthUtil.isUserAuthenticated()
    }

    Scaffold(bottomBar = {
        NavigationBar {
            TOP_LEVEL_DESTINATIONS.forEach { destination ->
                NavigationBarItem(
                    selected = currentRoute == destination.route,
                    onClick = {
                        navController.navigateTopLevel(destination.route)
                    },
                    icon = { Icon(imageVector = destination.icon, contentDescription = destination.label) },
                    label = { Text(text = destination.label) },
                    alwaysShowLabel = false
                )
            }
        }
    }, floatingActionButton = {
        if (isUserAuthenticated && fabConfig != null) {
            LargeFloatingActionButton(
                shape = RoundedCornerShape(20.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = {
                    fabConfig.onClick?.invoke()
                    if (currentRoute != fabConfig.destination.route) {
                        navController.navigate(fabConfig.destination.route)
                    }
                }) {
                AnimatedContent(targetState = fabConfig, label = "${fabConfig.destination.label} FAB") { fabState ->
                    Icon(
                        imageVector = fabState.destination.icon,
                        contentDescription = fabState.destination.label,
                        Modifier.size(28.dp)
                    )
                }
            }
        }
    }) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = currentRoute) {
                composable(Destination.HOME.route) {
                    HomeScreen(homeViewModel, setFabConfig)
                }
                composable(Destination.ACCOUNT.route) {
                    UserScreen(isUserLoggedInFromLogin, userViewModel, navigateToLogin, setFabConfig)
                }
                composable(Destination.NEW_QUESTION.route) {
                    FormScreen(preFill = null, setFabConfig)
                }
                composable(Destination.EDIT_QUESTION.route, arguments = listOf(navArgument("question_id") { type = NavType.StringType })) {
                    LaunchedEffect(key1 = true) {
                        setFabConfig(FabConfig.EditQuestion())
                    }
                    EmptyComingSoon()
                }
            }
        }
    }
}

sealed class FabConfig(val destination: Destination, val onClick: (() -> Unit)?) {
    class AddQuestion(onClick: (() -> Unit)? = null) : FabConfig(Destination.NEW_QUESTION, onClick)
    class EditQuestion(onClick: (() -> Unit)? = null) : FabConfig(Destination.EDIT_QUESTION, onClick)
    class EditUser(onClick: (() -> Unit)? = null) : FabConfig(Destination.EDIT_ACCOUNT, onClick)
    class SubmitForm(onClick: (() -> Unit)? = null) : FabConfig(Destination.SUBMIT, onClick)
}

val TOP_LEVEL_DESTINATIONS = listOf(
    Destination.HOME,
    Destination.ACCOUNT
)

sealed class Destination(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object HOME : Destination("home", Icons.Default.Home, "Home")
    data object ACCOUNT : Destination("account", Icons.Default.AccountCircle, "Account")
    data object EDIT_ACCOUNT : Destination("edit_user/{user_id}", Icons.Default.Edit, "Edit user")
    data object NEW_QUESTION : Destination("add_question", Icons.Default.Add, "New Question")
    data object EDIT_QUESTION : Destination("edit_question/{question_id}", Icons.Default.Edit, "Edit question")
    data object SUBMIT : Destination("home", Icons.Default.Done, "Submit form")
}

fun NavController.navigateTopLevel(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateTopLevel.graph.findStartDestination().id)
        launchSingleTop = true
    }
}
//
//@Preview
//@Composable
//fun PreviewHome() {
//    HomeNavigation()
//}