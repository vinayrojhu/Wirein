package com.example.wirein.model

data class User(
    val id: String,
    val name: String,
    val profileImage: String,
    val bio: String,
    val links: List<String>,
    val followerIds: List<String>,
    val followingIds: List<String>,
    val postIds: List<String>,
    val storyIds: List<String>
)