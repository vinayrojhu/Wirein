package com.example.wirein.ui.theme.Navigation

enum class Screen {
    SPLASH,
    HOME,
    SEARCH,
    CREATE_POST,
    REELS,
    PROFILE,
    CHAT_LIST,
    CHAT,
    FOLLOWERS,
    NOTIFICATION,
    VIEW_POST,
    VIEW_STORY,
    TWEET_LIST,
    TWEET,
    CREATE_TWEET
}

sealed class NavigationItem(val route: String) {
    object Splash : NavigationItem(Screen.SPLASH.name)
    object Home : NavigationItem(Screen.HOME.name)
    object Search : NavigationItem(Screen.SEARCH.name)
    object CreatePost : NavigationItem(Screen.CREATE_POST.name)
    object Reels : NavigationItem(Screen.REELS.name)
    object Profile : NavigationItem(Screen.PROFILE.name)
    object ChatList : NavigationItem(Screen.CHAT_LIST.name)
    object Chat : NavigationItem(Screen.CHAT.name)
    object Followers : NavigationItem(Screen.FOLLOWERS.name)
    object Notification : NavigationItem(Screen.NOTIFICATION.name)
    object ViewPost : NavigationItem(Screen.VIEW_POST.name)
    object ViewStory : NavigationItem(Screen.VIEW_STORY.name)
    object TweetList : NavigationItem(Screen.TWEET_LIST.name)
    object Tweet : NavigationItem(Screen.TWEET.name)
    object CreateTweet : NavigationItem(Screen.CREATE_TWEET.name)
}