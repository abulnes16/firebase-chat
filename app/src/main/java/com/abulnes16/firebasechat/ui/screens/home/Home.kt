package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.ui.components.HomeTabs

@Composable
fun HomeScreen(currentScreen: HomeDestinations, onTabChange: (HomeDestinations) -> Unit) {
  Text(text = "This is the home screen")
}

@Preview
@Composable
fun HomeScreenPreview() {

}