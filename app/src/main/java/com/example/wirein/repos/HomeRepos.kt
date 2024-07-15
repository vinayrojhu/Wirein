package com.example.wirein.repos

import com.example.wirein.api.WIREinAPI
import com.example.wirein.model.BaseResponse
import com.example.wirein.model.Notification
import com.example.wirein.model.Post
import com.example.wirein.model.Story
import com.example.wirein.model.Tweet
import com.example.wirein.model.User
import com.example.wirein.util.DemoData
import com.example.wirein.util.DemoData.tweets
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

@ActivityScoped
class HomeRepo @Inject constructor(
    private var wirEinAPI: WIREinAPI,
) {
    val tweetList = mutableListOf<Tweet>()

    suspend fun getUserResponse(): BaseResponse<User> {
        return wirEinAPI.getUsers()
    }

    suspend fun getPosts(): List<Post> {
        return wirEinAPI.getPosts().data
    }

    suspend fun getStories(): List<Story> {
        return wirEinAPI.getStories().data
    }

    suspend fun getNotifications(): List<Notification> {
        return wirEinAPI.getNotifications().data
    }

    suspend fun getTweets(): List<Tweet> {
        delay(5000L)
        for (i in 1..30) {
            tweetList.add(
                Tweet(
                    description = tweets.random(),
                    userId = "",
                    comments = tweets.subList(0, Random.nextInt(1, 4)),
                    likeCount = i * i,
                    retweetCount = i * 2,
                    bookmarkCount = 10 * i,
                    imageUrl = DemoData.urls.subList(0, 2),
                    timeStamp = System.currentTimeMillis()
                )
            )
        }
        return tweetList
    }
}