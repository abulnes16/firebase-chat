package com.abulnes16.firebasechat.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.firebasechat.data.Collections
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "[Authentication]"

class AuthViewModel(
    private val authProvider: FirebaseAuth,
    private val dbProvider: FirebaseFirestore
) : ViewModel() {
    private var _name by mutableStateOf("")
    private var _email by mutableStateOf("")
    private var _password by mutableStateOf("")
    private var _requestStatus by mutableStateOf(RequestState.NONE)


    val name: String
        get() = _name
    val email: String
        get() = _email
    val password: String
        get() = _password

    val requestStatus: RequestState
        get() = _requestStatus


    fun onChangeName(value: String) {
        _name = value
    }

    fun onChangeEmail(value: String) {
        _email = value
    }

    fun onChangePassword(value: String) {
        _password = value
    }

    fun onValidateForm(): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    fun onValidateSignIn(): Boolean {
        return _email.isNotEmpty() && _password.isNotEmpty()
    }


    fun onSignUp(onSuccess: () -> Unit, onFailed: () -> Unit) {
        _requestStatus = RequestState.LOADING
        viewModelScope.launch {
            try {
                val result = authProvider.createUserWithEmailAndPassword(_email, _password).await()
                val userId = result.user?.uid ?: ""
                val user = User(id = userId, name = _name)
                dbProvider.collection(Collections.USERS.collection).add(user).await()

                Log.d(TAG, "Success Signup ${result.user}")
                _requestStatus = RequestState.SUCCESS
                onSuccess()
            } catch (e: Exception) {
                _requestStatus = RequestState.FAILED
                Log.w(TAG, "Failed sign up ${e.message}")
                onFailed()
            }

        }
    }


    fun onSignIn(onSuccess: () -> Unit, onFailed: () -> Unit) {
        _requestStatus = RequestState.LOADING
        viewModelScope.launch {
            try {
                val result = authProvider.signInWithEmailAndPassword(_email, _password).await()
                Log.d(TAG, "Success Sign In ${result.user}")
                _requestStatus = RequestState.SUCCESS
                onSuccess()
            } catch (e: Exception) {
                _requestStatus = RequestState.FAILED
                Log.w(TAG, "Failed sign in ${e.message}")
                onFailed()
            }

        }
    }


}


class AuthViewModelFactory(
    private val authProvider: FirebaseAuth,
    private val dbProvider: FirebaseFirestore
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authProvider, dbProvider) as T
    }
}

