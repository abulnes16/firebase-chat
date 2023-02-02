package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.navigation.Home
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.ui.components.Chat
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.components.Screen
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.database.FirestoreService
import com.abulnes16.firebasechat.ui.components.LoadingList
import com.abulnes16.firebasechat.viewmodels.HomeViewModel
import com.abulnes16.firebasechat.viewmodels.HomeViewModelFactory

@Composable
fun HomeScreen(
    onChatClick: (Chat) -> Unit,
    onTabSelected: (HomeDestinations) -> Unit,
    currentScreen: HomeDestinations,
    modifier: Modifier = Modifier,
    chatViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(db = FirestoreService))
) {
    Scaffold(topBar = {
        HomeTabs(
            allTabs = homeTabs,
            onTabSelected = onTabSelected,
            currentScreen = currentScreen
        )
    }, modifier = modifier) {
        Screen(arrangement = Arrangement.Top) {
            LoadingList(
                loading = chatViewModel.requestState === RequestState.LOADING,
                data = chatViewModel.chats,
                emptyPlaceholder = R.string.empty_chats
            ) {
                if (it != null) {
                    Chat(
                        name = it.receiver,
                        lastMessage = it.messages.first().content,
                        date = it.messages.first().date.toString(),
                        onClick = { onChatClick(it) })
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