package com.abulnes16.firebasechat.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.firebasechat.data.RequestState
import com.abulnes16.firebasechat.database.FirestoreService
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "[Authentication]"

class AuthViewModel(
    private val authProvider: FirebaseAuth,
    private val db: FirestoreService
) : ViewModel() {
    private var _name by mutableStateOf("")
    private var _email by mutableStateOf("")
    private var _password by mutableStateOf("")
    private var _requestStatus by mutableStateOf(RequestState.NONE)
    private var _currentUser by mutableStateOf<FirebaseUser?>(null)


    val name: String
        get() = _name
    val email: String
        get() = _email
    val password: String
        get() = _password

    val requestStatus: RequestState
        get() = _requestStatus


    val currentUser: FirebaseUser?
        get() = _currentUser

    init {
        _currentUser = authProvider.currentUser
    }

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

    fun onThirdPartyComplete(result: FirebaseUser) {
        _currentUser = result
    }


    fun onSignUp(onFailed: () -> Unit) {
        _requestStatus = RequestState.LOADING
        viewModelScope.launch {
            try {
                val result = authProvider.createUserWithEmailAndPassword(_email, _password).await()
                val userId = result.user?.uid ?: ""
                val userResult = db.createUser(name, userId)

                if (!userResult) {
                    throw Exception("User wasn't saved")
                }


                Log.d(TAG, "Success Signup ${result.user}")
                _requestStatus = RequestState.SUCCESS
                clearInfo()
            } catch (e: Exception) {
                _requestStatus = RequestState.FAILED
                Log.w(TAG, "Failed sign up ${e.message}")
                onFailed()
            }

        }
    }


    fun onSignIn(onFailed: () -> Unit) {
        _requestStatus = RequestState.LOADING
        viewModelScope.launch {
            try {
                val result = authProvider.signInWithEmailAndPassword(_email, _password).await()
                Log.d(TAG, "Success Sign In ${result.user}")
                _requestStatus = RequestState.SUCCESS
                _currentUser = authProvider.currentUser
                clearInfo()

            } catch (e: Exception) {
                _requestStatus = RequestState.FAILED
                Log.w(TAG, "Failed sign in ${e.message}")
                onFailed()
            }

        }
    }

    fun onLogout() {
        try {
            authProvider.signOut()
            _currentUser = null

        } catch (e: Exception) {
            Log.d("AuthViewModel", e.message.toString())
        }

    }

    private fun clearInfo() {
        _email = ""
        _name = ""
        _password = ""
    }


}


class AuthViewModelFactory(
    private val authProvider: FirebaseAuth,
    private val db: FirestoreService
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authProvider, db) as T
    }
}

