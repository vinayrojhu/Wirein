package com.example.wirein.api

import com.example.wirein.model.BaseResponse
import com.example.wirein.model.Notification
import com.example.wirein.model.Post
import com.example.wirein.model.Story
import com.example.wirein.model.User
import retrofit2.http.GET

interface WIREinAPI {

    @GET("users")
    suspend fun getUsers(): BaseResponse<User>

    @GET("posts")
    suspend fun getPosts(): BaseResponse<Post>

    @GET("stories")
    suspend fun getStories(): BaseResponse<Story>

    @GET("notifications")
    suspend fun getNotifications(): BaseResponse<Notification>
}