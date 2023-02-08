package com.abulnes16.firebasechat.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.data.User
import com.abulnes16.firebasechat.data.mockMessages
import com.abulnes16.firebasechat.database.FirestoreService
import com.abulnes16.firebasechat.ui.components.MessageForm
import com.abulnes16.firebasechat.ui.components.MessageList
import com.abulnes16.firebasechat.viewmodels.ChatViewModel
import com.abulnes16.firebasechat.viewmodels.ChatViewModelFactory


@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
    ) {
        when (chatViewModel.requestState) {
            RequestState.LOADING ->
                CircularProgressIndicator()
            RequestState.FAILED -> {
                Text(text = "We couldn't load the conversation, please try again")
                Button(onClick = { chatViewModel.fetchChat() }) {
                    Text(text = "Retry")
                }
            }
            else ->
                if (chatViewModel.chat!!.messages.isNotEmpty()) {
                    MessageList(
                        messages = chatViewModel.chat!!.messages,
                        modifier = Modifier.weight(1f)
                    )
                }

        }


        MessageForm()
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {

    ChatScreen()
}