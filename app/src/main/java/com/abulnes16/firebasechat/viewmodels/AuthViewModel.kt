package com.abulnes16.firebasechat.viewmodels

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.abulnes16.firebasechat.data.*
import com.abulnes16.firebasechat.database.FirestoreService
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

private const val TAG = "[AuthViewModel]"

class AuthViewModel(
    private val authProvider: FirebaseAuth,
    private val db: FirestoreService
) : ViewModel() {

    var state by mutableStateOf(AuthState(currentUser = null, userData = null))
        private set


    fun onUserChange(event: UserEvent) {
        state = when (event) {
            is UserEvent.OnFirebaseUserEvent ->
                state.copy(currentUser = event.user)
            is UserEvent.OnUserEvent ->
                state.copy(userData = event.user)
        }
    }

    fun onLogout() {
        try {
            authProvider.signOut()
            state = state.copy(currentUser = null)

        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    fun fetchUserData() {
        // If the user is logged in we fetch the info
        if (state.currentUser != null) {
            viewModelScope.launch {
                try {
                    val userData = db.getUser(state.currentUser?.uid!!)
                    state = state.copy(userData = userData)
                } catch (e: Exception) {
                    Log.d(TAG, e.message.toString())
                }
            }
        }

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

