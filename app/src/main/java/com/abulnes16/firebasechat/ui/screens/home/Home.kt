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
import com.abulnes16.firebasechat.viewmodels.AuthViewModel
import com.abulnes16.firebasechat.viewmodels.AuthViewModelFactory
import com.abulnes16.firebasechat.viewmodels.HomeViewModel
import com.abulnes16.firebasechat.viewmodels.HomeViewModelFactory
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun HomeScreen(
    onChatClick: (Chat) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory(db = FirestoreService)),
) {

        Screen(arrangement = Arrangement.Top, modifier = modifier) {
            LoadingList(
                loading = homeViewModel.requestState === RequestState.LOADING,
                data = homeViewModel.chats,
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onChatClick = {})
}