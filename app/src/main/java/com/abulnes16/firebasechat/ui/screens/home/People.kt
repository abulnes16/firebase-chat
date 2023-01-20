package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abulnes16.firebasechat.data.mockUsers
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.People
import com.abulnes16.firebasechat.navigation.homeTabs
import com.abulnes16.firebasechat.ui.components.HomeTabs
import com.abulnes16.firebasechat.ui.components.PeopleCard
import com.abulnes16.firebasechat.ui.components.Screen

@Composable
fun PeopleScreen(
    onTabSelected: (HomeDestinations) -> Unit,
    currentScreen: HomeDestinations
) {
    Scaffold(
        topBar = {
            HomeTabs(
                allTabs = homeTabs,
                onTabSelected = onTabSelected,
                currentScreen = currentScreen
            )
        }
    ) {
        Screen(arrangement = Arrangement.Top) {
            LazyColumn() {
                items(mockUsers) { user ->
                    PeopleCard(name = user.name)
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview() {
    PeopleScreen(onTabSelected = {}, currentScreen = People)
}