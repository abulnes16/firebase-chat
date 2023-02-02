package com.abulnes16.firebasechat.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.data.User
import com.abulnes16.firebasechat.database.FirestoreService
import kotlinx.coroutines.launch

class UserViewModel(private val db: FirestoreService) : ViewModel() {

    private var _users by mutableStateOf<List<User>>(listOf())
    private var _requestState by mutableStateOf(RequestState.NONE)

    init {
        fetchUsers()
    }

    val users: List<User>
        get() = _users

    val requestState: RequestState
        get() = _requestState

    private fun fetchUsers() {
        _requestState = RequestState.LOADING
        viewModelScope.launch {
            val fetchedUsers = db.getUsers()
            _users = if (fetchedUsers.isNullOrEmpty()) {
                listOf()
            } else {
                fetchedUsers
            }
            _requestState = RequestState.SUCCESS
        }
    }

}


class UserViewModelFactory(
    private val db: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(db) as T
    }
}
