package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.mockChats
import com.abulnes16.firebasechat.navigation.Home
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.ui.components.Chat
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.components.Screen

@Composable
fun HomeScreen(
    onChatClick: (Chat) -> Unit,
    onTabSelected: (HomeDestinations) -> Unit,
    currentScreen: HomeDestinations
) {
    Scaffold(topBar = {
        HomeTabs(
            allTabs = homeTabs,
            onTabSelected = onTabSelected,
            currentScreen = currentScreen
        )
    }) {
        Screen(arrangement = Arrangement.Top) {
            LazyColumn() {
                items(mockChats) { chat ->
                    Chat(
                        name = chat.receiver,
                        lastMessage = chat.messages.first().content,
                        date = chat.messages.first().date.toString(),
                        onClick = { onChatClick(chat) }
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onChatClick = {}, currentScreen = Home, onTabSelected = {})
}