package com.abulnes16.firebasechat.data

import java.util.*

data class Message(
    val id: String,
    val content: String,
    val date: Date,
    val read: Boolean,
    val sender: String
)


val mockMessages = listOf<Message>(
    Message(id = "1", content = "Hola", date = Date(), read = true, sender = "1"),
    Message(id = "2", content = "¿Que tal?", date = Date(), read = true, sender = "2"),
    Message(id = "3", content = "Todo bien", date = Date(), read = true, sender = "1"),
    Message(id = "4", content = "¿Y vos?", date = Date(), read = true, sender = "2"),
    Message(id = "5", content = "Tudo bem?", date = Date(), read = true, sender = "1"),
    Message(id = "6", content = "Sim", date = Date(), read = true, sender = "2"),
)
