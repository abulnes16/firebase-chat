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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.abulnes16.firebasechat.navigation.FirebaseChatNavHost
import com.abulnes16.firebasechat.ui.theme.FirebaseChatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseChatApp()
        }
    }
}

@Composable
fun FirebaseChatApp() {
    FirebaseChatTheme {
        val navController = rememberNavController()

        Scaffold() {
            FirebaseChatNavHost(
                navController = navController,
                modifier = Modifier.padding(it)
            )
        }
    }
}
