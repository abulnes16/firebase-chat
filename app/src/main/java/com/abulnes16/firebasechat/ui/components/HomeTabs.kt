package com.abulnes16.firebasechat.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abulnes16.firebasechat.R
import com.abulnes16.firebasechat.navigation.Home
import com.abulnes16.firebasechat.navigation.HomeDestinations
import com.abulnes16.firebasechat.navigation.homeTabs

private val TabHeight = 50.dp

@Composable
fun HomeTabs(
    allTabs: List<HomeDestinations>,
    onTabSelected: (HomeDestinations) -> Unit,
    currentScreen: HomeDestinations,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(TabHeight)
    ) {
        val tabSize = 1f / allTabs.size
        Row(modifier.selectableGroup()) {
            allTabs.forEach { screen ->
                HomeTab(
                    name = screen.tabName,
                    icon = screen.icon,
                    selected = currentScreen == screen,
                    onSelected = { onTabSelected(screen) },
                    modifier = Modifier.weight(tabSize)
                )
            }
        }
    }
}

@Composable
fun HomeTab(
    @StringRes name: Int,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .height(TabHeight)
            .selectable(selected = selected, onClick = onSelected, role = Role.Tab)
    ) {
        val color =
            if (selected) MaterialTheme.colors.primary else LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(text = stringResource(name), color = color)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTabPreview() {
    HomeTab(R.string.home, Icons.Filled.Home, {}, true)
}

@Preview
@Composable
fun HomeTabsPreview() {
    HomeTabs(allTabs = homeTabs, onTabSelected = {}, currentScreen = Home)
}

