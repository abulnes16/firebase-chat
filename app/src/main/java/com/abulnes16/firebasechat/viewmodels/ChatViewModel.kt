package com.abulnes16.firebasechat.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.repository.FirestoreService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: FirestoreService) : ViewModel() {

    private var _chats by mutableStateOf<List<Chat?>>(listOf())
    private var _requestState by mutableStateOf(RequestState.NONE)

    init {
        fetchChats()
    }

    val chats: List<Chat?>
        get() = _chats

    val requestState: RequestState
        get() = _requestState


    private fun fetchChats() {
        _requestState = RequestState.LOADING
        viewModelScope.launch {
            val fetchedChats = repository.getChats()
            _chats = if (fetchedChats.isNullOrEmpty()) {
                listOf()
            } else {
                fetchedChats
            }
            _requestState = RequestState.SUCCESS
        }
    }

}


class ChatViewModelFactory(
    private val repository: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(repository) as T
    }
}
