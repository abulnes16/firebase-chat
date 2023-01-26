package com.abulnes16.firebasechat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.ui.screens.auth.SignInScreen
import com.abulnes16.firebasechat.ui.screens.auth.SignUpScreen
import com.abulnes16.firebasechat.ui.screens.home.ChatScreen
import com.abulnes16.firebasechat.ui.screens.home.HomeScreen
import com.abulnes16.firebasechat.ui.screens.home.PeopleScreen
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun FirebaseChatNavHost(
    navController: NavHostController,
    currentScreen: HomeDestinations,
    modifier: Modifier = Modifier,
) {
    val onTabSelected: (HomeDestinations) -> Unit =
        { screen -> navController.navigate(screen.route) }


    NavHost(
        navController = navController,
        startDestination = SignIn.route,
        modifier = modifier
    ) {

        // AuthScreens
        composable(SignIn.route) {
            SignInScreen(
                onSignUp = { navController.navigate(SignUp.route) },
                onSuccessSignIn = { navController.navigateToTop(Home.route) },
            )
        }
        composable(SignUp.route) {
            SignUpScreen(
                onSuccessSignUp = {
                    navController.navigateToTop(Home.route)
                },
                onGoToSignIn = { navController.popBackStack() },
            )
        }

        // HomeScreens
        composable(Home.route) {
            HomeScreen(
                onChatClick = { chat -> navController.navigateToChat(chat) },
                onTabSelected = onTabSelected,
                currentScreen = currentScreen
            )
        }
        composable(People.route) {
            PeopleScreen(
                onTabSelected = onTabSelected,
                currentScreen = currentScreen,
                onSelectPeople = { navController.navigateToChat(null) })
        }
        composable(route = Chats.routeWithArgs, arguments = Chats.arguments) { navBackStackEntry ->
            val chatId = navBackStackEntry.arguments?.getString(Chats.chatIdArg)
            ChatScreen(chatId)
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

fun NavHostController.navigateToChat(chat: Chat?) {
    this.navigate("${Chats.route}/${chat?.id}")
}