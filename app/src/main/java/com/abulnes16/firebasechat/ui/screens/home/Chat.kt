package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abulnes16.firebasechat.data.mockMessages
import com.abulnes16.firebasechat.ui.components.MessageForm
import com.abulnes16.firebasechat.ui.components.MessageList


@Composable
fun ChatScreen(chatId: String?, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        MessageList(messages = mockMessages, modifier = Modifier.weight(1f))
        MessageForm()
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(chatId = "")
}