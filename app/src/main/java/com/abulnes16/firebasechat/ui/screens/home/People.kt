package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.People
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.database.FirestoreService
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.components.LoadingList
import com.abulnes16.firebasechat.ui.components.PeopleCard
import com.abulnes16.firebasechat.ui.components.Screen
import com.abulnes16.firebasechat.viewmodels.UserViewModel
import com.abulnes16.firebasechat.viewmodels.UserViewModelFactory


@Composable
fun PeopleScreen(
    onSelectPeople: (String?, String) -> Unit,
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(db = FirestoreService))
) {

    Screen(arrangement = Arrangement.Top, modifier = modifier) {
        LoadingList(
            loading = userViewModel.requestState == RequestState.LOADING,
            data = userViewModel.users,
            emptyPlaceholder = R.string.empty_people
        ) {
            PeopleCard(name = it.name, onClick = { onSelectPeople(null, it.id) })
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview() {
    PeopleScreen(onSelectPeople = {_, _ ->})
}