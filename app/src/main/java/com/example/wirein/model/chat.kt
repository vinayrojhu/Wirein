package com.example.wirein.model

data class Chat(
    val senderImage: String,
    val receiverImage: String,
    val msgs: List<Message>,
)