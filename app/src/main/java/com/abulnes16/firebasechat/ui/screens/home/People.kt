package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.data.mockUsers
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.People
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.repository.FirestoreService
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.components.LoadingList
import com.abulnes16.firebasechat.ui.components.PeopleCard
import com.abulnes16.firebasechat.ui.components.Screen
import com.abulnes16.firebasechat.viewmodels.UserViewModel
import com.abulnes16.firebasechat.viewmodels.UserViewModelFactory


@Composable
fun PeopleScreen(
    onTabSelected: (HomeDestinations) -> Unit,
    onSelectPeople: (Chat?) -> Unit,
    currentScreen: HomeDestinations,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(repository = FirestoreService))
) {
    Scaffold(
        topBar = {
            HomeTabs(
                allTabs = homeTabs,
                onTabSelected = onTabSelected,
                currentScreen = currentScreen
            )
        },
        modifier = modifier
    ) {
        Screen(arrangement = Arrangement.Top) {
            LoadingList(
                loading = userViewModel.requestState == RequestState.LOADING,
                data = userViewModel.users,
                emptyPlaceholder = R.string.empty_people
            ) {
                PeopleCard(name = it.name, onClick = { onSelectPeople(null) })
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview() {
    PeopleScreen(onTabSelected = {}, currentScreen = People, onSelectPeople = {})
}