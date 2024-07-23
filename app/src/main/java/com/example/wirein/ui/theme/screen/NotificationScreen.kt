package com.example.wirein.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wirein.R
import com.example.wirein.model.ApiFailure
import com.example.wirein.model.BaseState
import com.example.wirein.ui.theme.components.CenterCircularProgressBar
import com.example.wirein.ui.theme.components.CenterErrorText
import com.example.wirein.ui.theme.components.PostSection
import com.example.wirein.ui.theme.components.PostTabView
import com.example.wirein.ui.theme.screen.viewmodel.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val notificationsState by homeViewModel.notifications.collectAsState()
    Column {
        TopAppBar(title = {
            Text(
                "Podcast", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier.padding(8.dp)
                )
            }
        },
            actions = {
                Row {
                    IconButton(onClick = { /* no-op */ }) {
                        Icon(painter = painterResource(id = R.drawable.global_network),
                            contentDescription = "globe",
                            modifier = Modifier.padding(8.dp))
                    }
                }
            })
        when (val state = notificationsState) {
            is BaseState.Failed -> {
                when (state.error) {
                    is ApiFailure.Unknown -> CenterErrorText(msg = state.error.error)
                }
            }

            BaseState.Loading -> {
                CenterCircularProgressBar()
            }

            is BaseState.Success -> {
//                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
//                    items(state.data) {
//                        NotificationItem(notification = it)
//                    }
//                }
                PostTabView(
                    imageWithText = listOf(
                        "Reels" to painterResource(id = R.drawable.podcast_profile_icon),
                        "Profile" to painterResource(id = R.drawable.podcastfinal),
                    )
                ) {
                    selectedTabIndex = it
                }
                when (selectedTabIndex) {
                    0 -> PodcastPosts()
                    1 -> AudioScreen()
                }





            }
        }
    }
}

//@Composable
//fun NotificationItem(notification: Notification) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        CircularImage(imageUrl = notification.image, imageSize = 45.dp)
//        Spacer(modifier = Modifier.width(8.dp))
//        Column(Modifier.fillMaxWidth()) {
//            Text(notification.title, fontSize = 14.sp)
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                DateUtil.getDateTime(notification.timeDate),
//                fontSize = 12.sp,
//                color = Color.Gray,
//                maxLines = 1,
//                modifier = Modifier.align(Alignment.End)
//            )
//        }
//    }
//}

//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun NotificationPreview() {
//    JetPackComposeBasicTheme {
//        Surface(
//            modifier = Modifier.fillMaxWidth(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            val homeViewModel: HomeViewModel = viewModel()
//
//            NotificationScreen(
//                homeViewModel = homeViewModel,
//                navController = rememberNavController()
//            )
//        }
//    }
//}
















@Composable
fun VideoPostBox(
    imageUrl: Int,
    title: String,
    author: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = imageUrl),
                contentDescription = "Video Thumbnail",
                modifier = Modifier
                    .width(630.dp)
                    .height(210.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = author,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "3 july, 2024",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
                IconButton(onClick = { /* Handle like action */ }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Like"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVideoPostBox() {
    VideoPostBox(
        imageUrl = R.drawable.podcast_video,
        title = "The Best Video Ever",
        author = "Alejandro Escamilla",
    )
}


@Composable
fun PodcastPosts(){
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState()) ){
        VideoPostBox(
            imageUrl = R.drawable.podcast_video,
            title = "The Best Podcast 1",
            author = "adfsadf s fakjsfh f s k"
        )
        VideoPostBox(
            imageUrl = R.drawable.podcast_video,
            title = "The Best Podcast 2",
            author = "djskafh akjs fhksdjhf khf fsk"
        )
        VideoPostBox(
            imageUrl = R.drawable.podcast_video,
            title = "The Best Podcast 3",
            author = "askjdfhkjf sk fksj skj f"
        )
        VideoPostBox(
            imageUrl = R.drawable.podcast_video,
            title = "The Best Podcast 4",
            author = "akkjsdfks faskjf "
        )
    }
}





data class AudioPodcast(
    val title: String,
    val description: String,
    val image: Int
)
@Composable
fun PodcastItem(podcast: AudioPodcast) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = ImageBitmap(width = 60, height = 60),
                contentDescription = podcast.title,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = podcast.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = podcast.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

val audiopodcasts = listOf(
    AudioPodcast(
        title = "All About Android 522: Guacamole Comes To Android",
        description = "Leo Laporte and Friends discuss the latest news and updates in the world of Android.",
        image = R.drawable.podcast_video
    ),
    AudioPodcast(
        title = "All About Android 513: Unlocking Android 12 Secrets",
        description = "Leo Laporte and Friends dive into the new features and hidden gems of Android 12.",
        image = R.drawable.podcast_video
    ),
    AudioPodcast(
        title = "All About Android 506: Android Adjacent",
        description = "Leo Laporte and Friends explore the Android ecosystem beyond the phone.",
        image = R.drawable.podcast_video
    ),
    AudioPodcast(
        title = "Best External Android Microphones",
        description = "A deep dive into the best microphones for recording your Android podcast.",
        image = R.drawable.podcast_video
    ),
    AudioPodcast(
        title = "MacBreak Weekly",
        description = "Leo Laporte and Friends discuss all things Apple.",
        image = R.drawable.podcast_video
    ),
    AudioPodcast(
        title = "Keep Productive: Latest Software Updates",
        description = "News and reviews of the latest productivity software.",
        image = R.drawable.podcast_video
    )
)

@Composable
fun AudioScreen(){
    val pairedPosts = audiopodcasts.chunked(2)
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(audiopodcasts) { podcast ->
            PodcastItem(podcast)
        }
    }
}