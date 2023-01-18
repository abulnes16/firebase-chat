package com.abulnes16.firebasechat.navigation

interface FirebaseChatDestinations {
    val route: String
}


object SignUp: FirebaseChatDestinations {
    override val route = "SignUpScreen"
}

object SignIn: FirebaseChatDestinations {
    override val route = "SignInScreen"
}

object Home: FirebaseChatDestinations {
    override val route = "HomeScreen"
}

object Chats: FirebaseChatDestinations {
    override val route = "ChatsScreen"
}

