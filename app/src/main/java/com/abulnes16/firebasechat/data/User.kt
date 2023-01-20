package com.abulnes16.firebasechat.data

data class User(val id: String, val name: String)


val mockUsers = listOf<User>(
    User(id="1", name="Angel"),
    User(id="2", name="David"),
    User(id="3", name="Ada"),
    User(id="4", name="Nohelia"),
    User(id="5", name="Allison"),
)