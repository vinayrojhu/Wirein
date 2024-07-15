package com.example.wirein.model

data class Post(
    val id: String,
    val userId: String,
    val postImage: String,
    val caption: String,
    val likeCount: Int = 0
)