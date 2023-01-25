package com.abulnes16.firebasechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abulnes16.firebasechat.navigation.FirebaseChatNavHost
import com.abulnes16.firebasechat.navigation.Home
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.theme.FirebaseChatTheme
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setContent {
            FirebaseChatApp(auth)
        }
    }
}

@Composable
fun FirebaseChatApp(auth: FirebaseAuth) {
    FirebaseChatTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination


        val currentHomeScreen =
            homeTabs.find { screen -> screen.route === currentDestination?.route } ?: Home

        FirebaseChatNavHost(
            navController = navController,
            authProvider = auth,
            currentScreen = currentHomeScreen
        )
    }

}
