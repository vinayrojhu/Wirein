package com.example.wirein.model

data class WirePost(
    val id: String,
    val userId: String,
    val postImage: String,
    val caption: String,
    val likeCount: Int = 0,

    val id2: String,
    val userId2: String,
    val postImage2: String,
    val caption2: String,
    val likeCount2: Int = 0
)