package com.abulnes16.firebasechat.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.Message
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.data.User
import com.abulnes16.firebasechat.database.FirestoreService
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(
    private val sender: User,
    private val receiver: String,
    private val chatId: String?,
    private val db: FirestoreService
) : ViewModel() {

    var chat by mutableStateOf<Chat?>(null)
        private set
    var message by mutableStateOf("")
        private set
    var requestState by mutableStateOf(RequestState.NONE)
        private set


    init {
        if (chatId != null) {
            fetchChat()
        }

    }

    fun onChangeMessage(content: String) {
        message = content
    }

    fun fetchChat() {
        requestState = RequestState.LOADING
        viewModelScope.launch {
            try {
                chat = db.getChat(chatId!!)
                requestState = RequestState.SUCCESS
            } catch (e: Exception) {
                requestState = RequestState.FAILED
            }

        }
    }

    fun sendMessage(onFailed: () -> Unit) {


        if (chatId == null) {
            createChat(onFailed)
        } else {
            updateChat(onFailed)
        }


    }

    private fun createChat(onFailed: () -> Unit) {
        println("Creating the chat")
        viewModelScope.launch {
            try {
                val currentDate = Date()
                val message = assembleMessage(currentDate)
                val newChat = Chat(
                    id = "",
                    listOf(message),
                    sender = sender.id,
                    receiver = receiver,
                    startDate = currentDate
                )

                val result = db.createChat(newChat)
                chat = newChat
                if (!result) {
                    throw Exception("Failed creating chat")
                }
                println("Chat created successfully")
            } catch (e: Exception) {
                onFailed()
                println("Chat failed to be created")
            }

        }
    }

    private fun updateChat(onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                val currentDate = Date()
                val message = assembleMessage(currentDate)
                val updatedList = chat!!.messages + message
                chat = chat!!.copy(messages = updatedList)
                val result = db.addMessage(chatId!!, updatedList)
                if (!result) {
                    throw Exception("Failed adding message")
                }
            } catch (e: Exception) {
                onFailed()
            }
        }
    }

    private fun assembleMessage(currentDate: Date): Message {
        return Message(
            id = "",
            content = message,
            date = currentDate,
            read = false,
            isMine = true,
            name = sender.name,
        )
    }

}

class ChatViewModelFactory(
    private val sender: User,
    private val receiver: String,
    private val chatId: String?,
    private val db: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(sender, receiver, chatId, db) as T
    }
}