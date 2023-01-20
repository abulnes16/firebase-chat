package com.abulnes16.firebasechat.data

import java.util.*

data class Chat(
    val id: String,
    val messages: List<Message>,
    val sender: String,
    val receiver: String,
    val startDate: Date
)

val mockChats = listOf<Chat>(
    Chat(id = "1", messages = mockMessages, sender = "1", receiver = "2", startDate = Date()),
    Chat(id = "2", messages = mockMessages, sender = "1", receiver = "3", startDate = Date()),
    Chat(id = "3", messages = mockMessages, sender = "1", receiver = "4", startDate = Date()),
    Chat(id = "4", messages = mockMessages, sender = "1", receiver = "5", startDate = Date()),
    Chat(id = "5", messages = mockMessages, sender = "1", receiver = "6", startDate = Date()),
)
