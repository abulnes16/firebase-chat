package com.abulnes16.firebasechat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.abulnes16.firebasechat.ui.screens.auth.SignInScreen
import com.abulnes16.firebasechat.ui.screens.auth.SignUpScreen
import com.abulnes16.firebasechat.ui.screens.home.ChatScreen
import com.abulnes16.firebasechat.ui.screens.home.HomeScreen

@Composable
fun FirebaseChatNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = SignIn.route,
        modifier = modifier
    ) {
        composable(SignIn.route) {
            SignInScreen()
        }
        composable(SignUp.route) {
            SignUpScreen()
        }
        composable(Home.route) {
            HomeScreen()
        }
        composable(Chats.route) {
            ChatScreen()
        }
    }

}