package com.example.wirein.model

data class Message(
    val msg: String,
    val timeStamp: String,
    val isSeen: Boolean = false,
    val userId: String,
)