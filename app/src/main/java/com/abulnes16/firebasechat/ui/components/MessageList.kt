package com.abulnes16.firebasechat.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.abulnes16.firebasechat.data.Message
import com.abulnes16.firebasechat.data.mockMessages

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier) {
        items(messages) { message ->
            Message(name = message.name, text = message.content, isMine = message.isMine)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageListPreview() {
    MessageList(messages = mockMessages)
}