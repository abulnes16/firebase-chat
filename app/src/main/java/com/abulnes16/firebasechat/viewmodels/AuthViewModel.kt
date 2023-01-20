package com.abulnes16.firebasechat.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {
    private var _isLoggedIn: MutableState<Boolean> = mutableStateOf(false)
    val isLoggedIn: State<Boolean>
        get() = _isLoggedIn

    fun login() {
        _isLoggedIn.value = true
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}