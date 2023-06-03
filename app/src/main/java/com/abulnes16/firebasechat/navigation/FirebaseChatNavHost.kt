package com.abulnes16.firebasechat.navigation

import android.util.Log
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.FormEvent
import com.abulnes16.firebasechat.data.User
import com.abulnes16.firebasechat.data.UserEvent
import com.abulnes16.firebasechat.database.FirestoreService
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.screens.auth.SignInScreen
import com.abulnes16.firebasechat.ui.screens.auth.SignUpScreen
import com.abulnes16.firebasechat.ui.screens.home.ChatScreen
import com.abulnes16.firebasechat.ui.screens.home.HomeScreen
import com.abulnes16.firebasechat.ui.screens.home.PeopleScreen
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.abulnes16.firebasechat.viewmodels.AuthViewModelFactory
import com.abulnes16.firebasechat.viewmodels.ChatViewModel
import com.abulnes16.firebasechat.viewmodels.ChatViewModelFactory
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
    if (authViewModel.state.currentUser != null) {

        LaunchedEffect(key1 = authViewModel.state.currentUser){
            authViewModel.fetchUserData()
        }

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

    Scaffold(
        topBar = {
           if(currentScreen != Chats){

               HomeTabs(
                   allTabs = homeTabs,
                   onTabSelected = onTabSelected,
                   currentScreen = currentScreen,
                   onLogout = { authViewModel.onLogout() }
               )
           }
        },
        floatingActionButton = {
            if (currentScreen == Home) {
                FloatingActionButton(onClick = { navController.navigate(People.route) }) {
                    Icon(imageVector = Icons.Filled.Create, contentDescription = null)
                }
            }
        },
        modifier = modifier
    ) {
        NavHost(navController = navController, startDestination = Home.route) {
            composable(Home.route) {
                HomeScreen(
                    onChatClick = { chatId, receiverId -> navController.navigateToChat(chatId, receiverId) },
                )
            }
            composable(People.route) {
                PeopleScreen(
                    onSelectPeople = {_, receiverId -> navController.navigateToChat(null, receiverId) }
                )
            }
            composable(
                route = Chats.routeWithArgs,
                arguments = Chats.arguments
            ) { navBackStackEntry ->
                val chatId = navBackStackEntry.arguments?.getString(Chats.chatIdArg)
                val receiverId = navBackStackEntry.arguments?.getString(Chats.receiverIdArg)
                val chatViewModel =
                    ChatViewModel(
                        chatId = chatId,
                        receiver = receiverId ?: "",
                        db = FirestoreService,
                        sender = authViewModel.state.userData ?: User(id = "", name = "")
                    )
                ChatScreen(chatViewModel = chatViewModel)
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
                onSuccessSignIn = { user ->
                    authViewModel.onUserChange(
                        UserEvent.OnFirebaseUserEvent(
                            user
                        )
                    )
                }
            )
        }
        composable(SignUp.route) {
            SignUpScreen(
                onSuccessSignUp = { authViewModel.onUserChange(UserEvent.OnFirebaseUserEvent(it)) },
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

fun NavHostController.navigateToChat(chatId: String?, receiverId: String?) {
    if(chatId != null){
        this.navigate("${Chats.route}?receiver_id={${receiverId}}&chat_id={${chatId}}")
    }else {
        this.navigate("${Chats.route}?receiver_id={${receiverId}}")
    }

}