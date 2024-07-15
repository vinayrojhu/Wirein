package com.example.wirein.ui.theme.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wirein.R
import com.example.wirein.model.ApiFailure
import com.example.wirein.model.BaseState
import com.example.wirein.model.Post
import com.example.wirein.model.User
import com.example.wirein.model.WirePost
import com.example.wirein.ui.theme.JetPackComposeBasicTheme
import com.example.wirein.ui.theme.LIGHT_BLUE
import com.example.wirein.ui.theme.Navigation.NavigationItem
import com.example.wirein.ui.theme.components.CenterCircularProgressBar
import com.example.wirein.ui.theme.components.CircularImage
import com.example.wirein.ui.theme.components.FFpostbox
import com.example.wirein.ui.theme.components.PostItem
import com.example.wirein.ui.theme.screen.viewmodel.HomeViewModel
import com.example.wirein.util.AppConstants
import com.example.wirein.util.AppConstants.Companion.MY_USER_ID

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    navController: NavController,
) {

    val userState = homeViewModel.users.collectAsState()
    val storiesState =
        homeViewModel.stories.collectAsState()
    val postsState =
        homeViewModel.posts.collectAsState()
    val bottomSheet = rememberModalBottomSheetState()
    var isBottomSheetOpened by remember {
        mutableStateOf(false)
    }
    if (isBottomSheetOpened) {
        ModalBottomSheet(
            sheetState = bottomSheet,
            onDismissRequest = {
                isBottomSheetOpened = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Options",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Unfollow",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Go to profile",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "share")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Share",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
    when (val state = userState.value) {
        is BaseState.Failed -> {
            when (state.error) {
                is ApiFailure.Unknown -> {
                    Button(onClick = {
                        homeViewModel.getUsers()
                        homeViewModel.getPosts()
                        homeViewModel.getStories()
                    }) {
                        Text("${state.error.error}\nRetry")
                    }
                }
            }
        }

        BaseState.Loading -> {
            CenterCircularProgressBar()
        }

        is BaseState.Success -> {
            val user = state.data.find {
                it.id == MY_USER_ID
            }
            if (user != null) {
                val followings =
                    homeViewModel.getUsersByIds(state.data, user.followingIds + user.id)

                Column(modifier = modifier) {
                    TopAppBar(title = {
                        Text(
                            "WIREin",
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.ExtraBold,
                        )
                    }, actions = {
                        Row {
                            IconButton(onClick = {
                                navController.navigate(NavigationItem.Notification.route)

                            }) {
                                Icon(
                                    painterResource(id = R.drawable.microphone),
                                    contentDescription = null,
                                    Modifier.padding(8.dp)
                                )
                            }
//                            IconButton(onClick = {
//                                navController.navigate(NavigationItem.ChatList.route)
//
//                            }) {
//                                Icon(
//                                    painterResource(id = R.drawable.ic_chat),
//                                    contentDescription = null,
//                                    Modifier.padding(8.dp)
//                                )
//                            }
                        }
                    })
                    LazyColumn {
                        item {
                            LazyRow(Modifier.padding(vertical = 8.dp)) {
                                item {
                                    Box(
                                        contentAlignment = Alignment.BottomEnd ,
                                    ) {
//                                        CircularImage(                          // story-> Text
//                                            imageUrl = user.profileImage,
//                                            isBorderVisible = false,
//                                            isNameVisible = true,
//                                            name = "Your story",
//                                            modifier = Modifier
//                                                .padding(start = 16.dp, end = 8.dp)
//                                                .clickable {
//                                                    navController.navigate(NavigationItem.CreatePost.route)
//                                                },
//                                        )
//                                        Icon(
//                                            tint = LIGHT_BLUE,
//                                            imageVector = Icons.Default.AddCircle,
//                                            contentDescription = null,
//                                            modifier = Modifier
//                                                .padding(bottom = 18.dp, end = 4.dp)
//                                                .clip(CircleShape)
//                                                .background(Color.White)
//                                                .border(
//                                                    width = 1.dp,
//                                                    color = Color.White,
//                                                    shape = CircleShape
//                                                )
//                                        )

                                        Column(Modifier.padding(start = 10.dp, top = 10.dp, end = 5.dp)) {
                                            Text(text = "  Top",
                                                fontStyle = FontStyle.Normal,
                                                color = Color.DarkGray,
                                                textAlign = TextAlign.Center)
                                            Text(text = "Stories",
                                                fontStyle = FontStyle.Normal,
                                                color = Color.DarkGray,
                                                textAlign = TextAlign.Center)
                                        }


                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                                when (val s = storiesState.value) {
                                    is BaseState.Failed -> {
                                        // no-op
                                    }

                                    BaseState.Loading -> {
                                        // no-op
                                    }

                                    is BaseState.Success -> {
                                        val stories = s.data.filter { story ->
                                            user.followingIds.any {
                                                story.userId == it
                                            }
                                        }.distinctBy { it.userId }
                                        items(stories) { story ->
                                            val usr = followings.find {
                                                story.userId == it.id
                                            }
                                            if (usr != null) {
                                                CircularImage(
                                                    imageUrl = usr.profileImage,
                                                    isBorderVisible = true,
                                                    isNameVisible = true,
                                                    isAnimated = true,
                                                    modifier = Modifier
                                                        .padding(horizontal = 8.dp)
                                                        .clickable {
                                                            navController.navigate(
                                                                "${NavigationItem.ViewStory.route}/${story.id}/${story.userId}"
                                                            )
                                                        },
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        when (val s = postsState.value) {                    // posts toggle
                            is BaseState.Failed -> {}
                            BaseState.Loading -> {}
                            is BaseState.Success -> {
                                val posts = s.data.filter { post ->
                                    (user.followingIds + MY_USER_ID).any {
                                        post.userId == it
                                    }
                                }
                                items(posts) { post ->
                                    val usr = followings.find {
                                        post.userId == it.id
                                    }
                                    if (usr != null) {
//                                        PostItem(post, usr, onImageClick = {
//                                            navController.navigate(
//                                                "${NavigationItem.Profile.route}/${usr.id}"
//                                            )
//                                        }) {
//                                            isBottomSheetOpened = !isBottomSheetOpened
//                                            // open bottom sheet with more options
//                                        }

//                                         MyScreen()

                                        FFpostbox(
                                            heading = "heading",
                                            description = "dfkvmk dfhv sidhv hv sduv dvvudy uhc uv dvguyg va" ,
                                            image = painterResource(id = R.drawable.study_in_australia),
                                            heading2 = "heading 02",
                                            description2 = "fsdf df asdkh fsdjkh sd ",
                                            image2 = painterResource(id = R.drawable.criminal_laws))
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                CenterCircularProgressBar()
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            val homeViewModel: HomeViewModel = viewModel()

            HomeScreen(
                homeViewModel = homeViewModel,
                navController = rememberNavController()
            )
        }
    }
}



//@Composable
//fun MyScreen() {
//    val post = WirePost(
//        id = "1",
//        userId = "user1",
//        postImage = "https://example.com/image1",
//        caption = "This is a sample post",
//        likeCount = 10,
//        postImage2 = "https://example.com/image2",
//        caption2 = "This is a sample post 2",
//        id2 = "2",
//        userId2 = "user2"
//    )
//
//    val user = User(
//        id = AppConstants.MY_USER_ID,
//        name = "Kaushal",
//        profileImage = "https://cdn.pixabay.com/photo/2023/05/23/15/26/bengal-cat-8012976_1280.jpg",
//        bio = "Android developer | Nature Lover",
//        links = listOf("https://github.com//KaushalVasava"),
//        followerIds = listOf("12346", "12347", "12348", "12349"),
//        followingIds = listOf("12346", "12347", "12348", "12349", "12345"),
//        postIds = listOf(
//            "123464",
//            "123465",
//            "123466",
//            "123467",
//            "123468",
//            "123469"
//        ),
//        storyIds = listOf("12411", "12412", "12413", "12429", "12428")
//    )
//
//    TogglePostItem(
//        post = post,
//        user = user,
//        onImageClick = { /*no-op*/ },
//        onMoreClick = { /*no-op*/ }
//    )
//}