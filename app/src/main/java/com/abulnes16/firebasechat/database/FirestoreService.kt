package com.abulnes16.firebasechat.database

import android.util.Log
import com.abulnes16.firebasechat.data.Chat
import com.abulnes16.firebasechat.data.Chat.Companion.toChats
import com.abulnes16.firebasechat.data.Collections
import com.abulnes16.firebasechat.data.User
import com.abulnes16.firebasechat.data.User.Companion.toUserList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreService {
    private const val TAG = "FirebaseService"

    suspend fun getChats(): List<Chat?>? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection(Collections.CHATS.collection).get().await().toChats()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            null
        }
    }

    suspend fun getUsers(): List<User>? {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection(Collections.USERS.collection).get().await().toUserList()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun createUser(name: String, id: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        return try {
            val user = User(id = id, name = name)
            db.collection(Collections.USERS.collection).add(user).await()
            true
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            false
        }
    }

}