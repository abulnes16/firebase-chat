package com.abulnes16.firebasechat.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.abulnes16.firebasechat.R

interface FirebaseChatDestinations {
    val route: String
}

interface HomeDestinations : FirebaseChatDestinations {
    val tabName: Int
    val icon: ImageVector
}


object SignUp : FirebaseChatDestinations {
    override val route = "SignUpScreen"
}

object SignIn : FirebaseChatDestinations {
    override val route = "SignInScreen"
}

object Home : FirebaseChatDestinations, HomeDestinations {
    override val route = "HomeScreen"
    override val tabName: Int = R.string.home
    override val icon: ImageVector = Icons.Filled.Home
}

object People : FirebaseChatDestinations, HomeDestinations {
    override val route = "PeopleScreen"
    override val tabName: Int = R.string.people
    override val icon: ImageVector = Icons.Filled.Person
}

object Chats : FirebaseChatDestinations {
    override val route = "ChatsScreen"
    const val chatIdArg = "chat_id"
    val routeWithArgs = "${route}/{${chatIdArg}}"
    val arguments = listOf(
        navArgument(chatIdArg) { type = NavType.StringType }
    )
}

val homeTabs = listOf(Home, People)