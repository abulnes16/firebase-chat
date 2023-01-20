package com.abulnes16.firebasechat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.abulnes16.firebasechat.ui.screens.auth.SignInScreen
import com.abulnes16.firebasechat.ui.screens.auth.SignUpScreen
import com.abulnes16.firebasechat.ui.screens.home.ChatScreen
import com.abulnes16.firebasechat.ui.screens.home.HomeScreen
import com.abulnes16.firebasechat.ui.screens.home.PeopleScreen
import com.abulnes16.firebasechat.viewmodels.AuthViewModel

@Composable
fun FirebaseChatNavHost(
    navController: NavHostController,
    currentScreen: HomeDestinations,
    onAuth: () -> Unit,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = SignIn.route,
        modifier = modifier
    ) {
        // AuthScreens
        composable(SignIn.route) {
            SignInScreen(onSignUp = { navController.navigate(SignUp.route) })
        }
        composable(SignUp.route) {
            SignUpScreen(
                onSuccessSignUp = {
                    onAuth()
                    navController.navigateToTop(Home.route)
                },
                onGoToSignIn = { navController.popBackStack() })
        }

        // HomeScreens
        composable(Home.route) {
            HomeScreen(
                currentScreen,
                onTabChange = { screen -> navController.navigate(screen.route) })
        }
        composable(People.route) {
            PeopleScreen()
        }
        composable(Chats.route) {
            ChatScreen()
        }

    }
}

fun NavHostController.navigateToTop(route: String) {
    this.navigate(route) {
        popUpTo(this@navigateToTop.graph.findStartDestination().id) {
            saveState = true
        }
    }
}