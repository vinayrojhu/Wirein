package com.example.wirein.ui.theme.Navigation.navHost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.wirein.ui.theme.Navigation.NavigationItem
import com.example.wirein.ui.theme.screen.ChatListScreen
import com.example.wirein.ui.theme.screen.ChatScreen
import com.example.wirein.ui.theme.screen.CreatePostScreen
import com.example.wirein.ui.theme.screen.CreateTweetScreen
import com.example.wirein.ui.theme.screen.HomeScreen
import com.example.wirein.ui.theme.screen.NotificationScreen
import com.example.wirein.ui.theme.screen.ProfileScreen
import com.example.wirein.ui.theme.screen.ReelsScreen
import com.example.wirein.ui.theme.screen.SearchScreen
import com.example.wirein.ui.theme.screen.SplashScreen
import com.example.wirein.ui.theme.screen.TweetListScreen
import com.example.wirein.ui.theme.screen.TweetScreen
import com.example.wirein.ui.theme.screen.UserFollowListScreen
import com.example.wirein.ui.theme.screen.ViewPostScreen
import com.example.wirein.ui.theme.screen.ViewStory
import com.example.wirein.ui.theme.screen.viewmodel.HomeViewModel
import com.example.wirein.util.AppConstants.Companion.MY_USER_ID

@Composable
fun AppNavHost(
    homeViewModel: HomeViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var isSplashScreenFinished by rememberSaveable {
        mutableStateOf(false)
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if(isSplashScreenFinished){
            NavigationItem.Home.route
        } else {
            NavigationItem.Splash.route
        }
    ) {
        composable(NavigationItem.Splash.route){
            SplashScreen {
                navController.navigate(NavigationItem.Home.route)
                isSplashScreenFinished = true
            }
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(NavigationItem.Search.route) {
            SearchScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(NavigationItem.CreatePost.route) {
            CreatePostScreen()
        }
        composable(NavigationItem.TweetList.route) {
            TweetListScreen(homeViewModel, navController)
        }
        composable(
            "${NavigationItem.Tweet.route}/{tweetId}",
            arguments = listOf(
                navArgument("tweetId") {
                    type = NavType.StringType
                },
            )
        ) {
            val tweetId = it.arguments?.getString("tweetId")
            tweetId?.let { id ->
                TweetScreen(id, homeViewModel, navController)
            }
        }
        composable(NavigationItem.CreateTweet.route) {
            CreateTweetScreen(homeViewModel = homeViewModel, navController = navController)
        }
        composable(NavigationItem.Reels.route) {
            ReelsScreen(homeViewModel = homeViewModel, navController)
        }
        composable(NavigationItem.ChatList.route) {
            ChatListScreen(homeViewModel, navController = navController)
        }
        composable(
            "${NavigationItem.Followers.route}/{isFollowing}/{userId}",
            arguments = listOf(
                navArgument("isFollowing") {
                    type = NavType.BoolType
                },
                navArgument("userId") {
                    type = NavType.StringType
                },
            )
        ) {
            val isFollower = it.arguments?.getBoolean("isFollowing") ?: false
            val userId = it.arguments?.getString("userId") ?: MY_USER_ID
            UserFollowListScreen(
                homeViewModel = homeViewModel,
                isFollowing = isFollower,
                userId = userId,
                navController = navController
            )
        }
        composable("${NavigationItem.Chat.route}/{userId}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.StringType
                }
            )) {
            val userId = it.arguments?.getString("userId") ?: "userid"
            ChatScreen(userId, homeViewModel = homeViewModel, navController = navController)
        }
        composable(
            "${NavigationItem.Profile.route}/{userid}",
            arguments = listOf(
                navArgument("userid") {
                    type = NavType.StringType
                }
            )) {
            val userId = it.arguments?.getString("userid")
            ProfileScreen(
                userId = userId ?: MY_USER_ID,
                homeViewModel = homeViewModel,
                navController = navController
            )
          }
        composable("${NavigationItem.ViewPost.route}/{postId}",
            arguments = listOf(
                navArgument("postId") {
                    type = NavType.StringType
                }
            )) {
            val postId = it.arguments?.getString("postId")
            postId?.let { id ->
                ViewPostScreen(id, homeViewModel = homeViewModel, navController = navController)
            }
        }
        composable("${NavigationItem.ViewStory.route}/{storyId}/{userId}",
            arguments = listOf(
                navArgument("storyId") {
                    type = NavType.StringType
                }, navArgument("userId") {
                    type = NavType.StringType
                }
            )) {
            val storyId = it.arguments?.getString("storyId")
            val userId = it.arguments?.getString("userId")
            if (storyId != null && userId != null) {
                ViewStory(
                    storyId,
                    userId,
                    homeViewModel = homeViewModel,
                    navController = navController
                )
            }
        }
        composable(NavigationItem.Notification.route) {
            NotificationScreen(homeViewModel = homeViewModel, navController = navController)
        }
    }
}