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
    private val receiver: User,
    private val sender: User,
    private val chatId: String?,
    private val db: FirestoreService
) : ViewModel() {

    private var _chat by mutableStateOf<Chat?>(null)
    private var _message by mutableStateOf("")
    private var _requestState by mutableStateOf(RequestState.NONE)


    val chat: Chat?
        get() = _chat

    val requestState: RequestState
        get() = _requestState

    val message: String
        get() = _message

    init {
        if (chatId != null) {
            fetchChat()
        }

    }

    fun fetchChat() {
        _requestState = RequestState.LOADING
        viewModelScope.launch {
            try {
                _chat = db.getChat(chatId!!)
                _requestState = RequestState.SUCCESS
            } catch (e: Exception) {
                _requestState = RequestState.FAILED
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
        viewModelScope.launch {
            try {
                val currentDate = Date()
                val message = assembleMessage(currentDate)
                val chat = Chat(
                    id = "",
                    listOf(message),
                    sender = sender.id,
                    receiver = receiver.id,
                    startDate = currentDate
                )

                val result = db.createChat(chat)
                _chat = chat
                if (!result) {
                    throw Exception("Failed creating chat")
                }
            } catch (e: Exception) {
                onFailed()
            }

        }
    }

    private fun updateChat(onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                val currentDate = Date()
                val message = assembleMessage(currentDate)
                val updatedList = _chat!!.messages + message
                _chat = _chat!!.copy(messages = updatedList)
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
            content = _message,
            date = currentDate,
            read = false,
            isMine = true,
            name = sender.name,
        )
    }

}

class ChatViewModelFactory(
    private val sender: User,
    private val receiver: User,
    private val chatId: String?,
    private val db: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(sender, receiver, chatId, db) as T
    }
}