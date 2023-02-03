package com.abulnes16.firebasechat.navigation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.database.FirestoreService
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.screens.auth.SignInScreen
import com.abulnes16.firebasechat.ui.screens.auth.SignUpScreen
import com.abulnes16.firebasechat.ui.screens.home.ChatScreen
import com.abulnes16.firebasechat.ui.screens.home.HomeScreen
import com.abulnes16.firebasechat.ui.screens.home.PeopleScreen
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.abulnes16.firebasechat.viewmodels.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun FirebaseChatNavHost(
    navController: NavHostController,
    currentScreen: HomeDestinations,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(
            authProvider = Firebase.auth,
            db = FirestoreService
        )
    )
) {
    if (authViewModel.currentUser != null) {
        HomeNavHost(
            navController = navController,
            currentScreen = currentScreen,
            authViewModel = authViewModel
        )
    } else {
        AuthNavHost(navController = navController, authViewModel)
    }
}


@Composable
fun HomeNavHost(
    navController: NavHostController,
    currentScreen: HomeDestinations,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val onTabSelected: (HomeDestinations) -> Unit =
        { screen -> navController.navigate(screen.route) }

    Scaffold(topBar = {
        HomeTabs(
            allTabs = homeTabs,
            onTabSelected = onTabSelected,
            currentScreen = currentScreen,
            onLogout = { authViewModel.onLogout() }
        )
    }, modifier = modifier) {
        NavHost(navController = navController, startDestination = Home.route) {
            composable(Home.route) {
                HomeScreen(
                    onChatClick = { chat -> navController.navigateToChat(chat) },
                )
            }
            composable(People.route) {
                PeopleScreen(
                    onSelectPeople = { navController.navigateToChat(null) }
                )
            }
            composable(
                route = Chats.routeWithArgs,
                arguments = Chats.arguments
            ) { navBackStackEntry ->
                val chatId = navBackStackEntry.arguments?.getString(Chats.chatIdArg)
                ChatScreen(chatId)
            }
        }
    }
}

@Composable
fun AuthNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = SignIn.route, modifier = modifier) {
        composable(SignIn.route) {
            SignInScreen(
                onSignUp = { navController.navigate(SignUp.route) },
                authViewModel = authViewModel,
                onSuccessThirdPartySignIn = { user -> authViewModel.onThirdPartyComplete(user) }
            )
        }
        composable(SignUp.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onGoToSignIn = { navController.popBackStack() },
            )
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