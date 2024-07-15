package com.example.wirein.model

data class Story(
    val id: String,
    val userId: String,
    val name: String? = null,
    val image: String,
    val likeCount: Int = 0
)