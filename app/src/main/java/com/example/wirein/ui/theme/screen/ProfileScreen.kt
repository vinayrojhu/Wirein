package com.example.wirein.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wirein.R
import com.example.wirein.model.UserType
import com.example.wirein.ui.theme.JetPackComposeBasicTheme
import com.example.wirein.ui.theme.Navigation.NavigationItem
import com.example.wirein.ui.theme.components.ButtonSection
import com.example.wirein.ui.theme.components.CenterCircularProgressBar
import com.example.wirein.ui.theme.components.HighlightSection
import com.example.wirein.ui.theme.components.MiddlePart
import com.example.wirein.ui.theme.components.PostSection
import com.example.wirein.ui.theme.components.PostTabView
import com.example.wirein.ui.theme.components.ProfileDescription
import com.example.wirein.ui.theme.screen.viewmodel.HomeViewModel
import com.example.wirein.util.AppConstants.Companion.MY_USER_ID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    userId: String,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val userState by remember {
        mutableStateOf(homeViewModel.getUserById(userId))
    }
    val admin by remember {
        mutableStateOf(homeViewModel.getUserById(MY_USER_ID))
    }
    val posts by remember {
        mutableStateOf(
            homeViewModel.getPosts(userState?.postIds ?: emptyList())
        )
    }
    val stories by remember {
        mutableStateOf(
            homeViewModel.getStories(userState?.storyIds ?: emptyList())
        )
    }
    val userType = if (userId == MY_USER_ID) {
        UserType.ADMIN
    } else if (admin!!.followingIds.any {
            it == userId
        }) {
        UserType.FOLLOWING
    } else {
        UserType.FOLLOWER
    }
    val user = userState
    if (user != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(user.id, fontSize = 18.sp) },
                actions = {
                    Row {
//                        IconButton(onClick = {
//                            navController.navigate(NavigationItem.Notification.route)
//                        }) {
//                            Icon(
//                                imageVector = Icons.Default.Notifications,
//                                contentDescription = "notification",
//                                modifier = Modifier.padding(8.dp)
//                            )
//                        }
                        IconButton(onClick = { /* no-op */ }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(2.dp))
            MiddlePart(
                user, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                navController
            )
            Spacer(modifier = Modifier.height(2.dp))
            ProfileDescription(
                displayName = "Vinay",
                description = user.bio,
                url = "www.indicwire.com",                               //user.links.firstOrNull(),
                followedBy = listOf("Vinay, Jerry"),
                otherCount = 34,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ButtonSection(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                userType = userType
            )
//            Spacer(modifier = Modifier.height(16.dp))
//            HighlightSection(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                highlights = stories,
//                navController = navController
//            )
            Spacer(modifier = Modifier.height(16.dp))
            PostTabView(
                imageWithText = listOf(
                    "Posts" to painterResource(id = R.drawable.node_icon6),
                    "Reels" to painterResource(id = R.drawable.podcast_profile_icon),
                    "Profile" to painterResource(id = R.drawable.live_broadcast_icon),
                )
            ) {
                selectedTabIndex = it
            }
            when (selectedTabIndex) {
                0 -> PostSection(
                    posts = posts,
                    modifier = Modifier.fillMaxWidth(),
                    navController
                )
            }
        }
    } else {
        CenterCircularProgressBar()
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProfilePreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val homeViewModel: HomeViewModel = viewModel()

            ProfileScreen(
                MY_USER_ID,
                homeViewModel,
                navController = rememberNavController()
            )
        }
    }
}