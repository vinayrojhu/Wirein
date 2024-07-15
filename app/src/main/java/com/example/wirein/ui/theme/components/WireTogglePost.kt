package com.example.wirein.ui.theme.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wirein.R


@Composable
fun FFpostbox(
    heading: String,
    description: String,
    image: Painter,
    heading2: String,
    description2: String,
    image2: Painter
) {
    // State to store the visibility of the heading, image, and description
    val (showContent, setShowContent) = remember { mutableStateOf(true) }

    // Content to be displayed when the heading, image, and description are hidden
    val alternateContent = @Composable {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = image2,
                    contentDescription = "Image in the box",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(1f)
                        .background(Color.White)
                        .padding(5.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        androidx.compose.material3.Text(
                            text = heading2,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis
                        )
                        androidx.compose.material3.Text(
                            text = description2,
                            color = Color.Black,
                            fontSize = 14.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .align(Alignment.Bottom)
                        .clickable { }
                ) {
                    androidx.compose.material3.Text(
                        text = "Read",
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_arrow_96),
                        contentDescription = "read more",
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .weight(1f)
                            .padding(start = 5.dp)
                    )
                }
            }
        }
    }
    Surface(modifier = Modifier
        .padding(5.dp)
        .clip(RoundedCornerShape(10.dp)),
        color = Color.Black) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)
        ) {
            // Hide the heading, image, and description when the toggle button is clicked
            if (showContent) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(225.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = image,
                        contentDescription = "Image in the box",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .padding(5.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(1f)
                            .background(Color.White)
                            .padding(5.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            androidx.compose.material3.Text(
                                text = heading,
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                            androidx.compose.material3.Text(
                                text = description,
                                color = Color.Black,
                                fontSize = 14.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .background(Color.White)
                            .align(Alignment.Bottom)
                            .clickable { }
                    ) {
                        androidx.compose.material3.Text(
                            text = "Read",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icons8_arrow_96),
                            contentDescription = "read more",
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .weight(1f)
                                .padding(start = 5.dp)
                        )
                    }
                }
            }

            // Conditionally render the alternate content when the heading, image, and description are hidden
            if (!showContent) {
                alternateContent()
            }
            // Toggle button to showand hide the heading, image, and description
            ToggleContentButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                showContent = showContent,
                onToggle = { setShowContent(!showContent) }
            )


        }
    }


}

@Composable
fun ToggleContentButton(
    modifier: Modifier = Modifier,
    showContent: Boolean,
    onToggle: () -> Unit
) {
    // Animate the height of the toggle button
    val height by animateDpAsState(
        if (showContent) 0.dp else 40.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable(onClick = onToggle)
    ) {
        androidx.compose.material3.Text(
            text = if (showContent) "Flip News" else "Flop News",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

























//@Composable
//fun TogglePostItem(
//    post: WirePost,
//    user: User,
//    onImageClick: () -> Unit,
//    onMoreClick: () -> Unit,
//) {
//    var likes by remember { mutableStateOf(post.likeCount) }
//    var isExpanded by remember { mutableStateOf(false) }
//
//    Column(Modifier.padding(vertical = 4.dp)) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            CircularImage(
//                imageUrl = user.profileImage,
//                imageSize = 40.dp,
//                modifier = Modifier
//                    .padding(vertical = 8.dp, horizontal = 16.dp)
//                    .clickable {
//                        onImageClick()
//                    }
//            )
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(vertical = 4.dp)
//            ) {
//                Text(user.name)
//                Spacer(modifier = Modifier.height(4.dp))
//                Text(
//                    "location",
//                    fontSize = 12.sp,
//                    color = Color.Gray,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            IconButton(onClick = {
//                onMoreClick()
//            }) {
//                Icon(
//                    imageVector = Icons.Default.MoreVert,
//                    contentDescription = null,
//                    Modifier.padding(8.dp)
//                )
//            }
//        }
//        PostImage(imageUrl = post.postImage)
//        Spacer(modifier = Modifier.height(4.dp))
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(start = 8.dp)
//        ) {
//            var isFavorite by remember { mutableStateOf(false) }
//            var isBookmarked by remember { mutableStateOf(false) }
//            ToggleIconButton(
//                enableTint = Color.Red,
//                enableIcon = rememberVectorPainter(image = Icons.Filled.Favorite),
//                disableIcon = rememberVectorPainter(image = Icons.Filled.FavoriteBorder),
//                initialState = isFavorite
//            ) {
//                if (it)
//                    likes++
//                else
//                    likes--
//                isFavorite =!isFavorite
//            }
//            IconButton(onClick = {
//                //no-op
//            }) {
//                Icon(
//                    painterResource(id = R.drawable.ic_comment),
//                    contentDescription = null,
//                    Modifier.padding(vertical = 8.dp)
//                )
//            }
//            IconButton(onClick = { /*no-op*/ }) {
//                Icon(
//                    Icons.Default.Send,
//                    contentDescription = null,
//                    modifier = Modifier.padding( vertical = 8.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//            ToggleIconButton(
//                enableTint = MaterialTheme.colorScheme.onBackground,
//                enableIcon = painterResource(id = R.drawable.ic_bookmark),
//                disableIcon = painterResource(id = R.drawable.ic_bookmark_border),
//                initialState = isBookmarked
//            ) {
//                isBookmarked =!isBookmarked
//            }
//        }
//        AnimatedContent(
//            targetState = likes,
//            transitionSpec = { fadeIn() + scaleIn() togetherWith fadeOut() + scaleOut() },
//            label = "likes"
//        ) {
//            Text(
//                "$it likes",
//                Modifier.padding(horizontal = 16.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(4.dp))
//        if (isExpanded) {
//            Column {
//                Text(
//                    text = post.caption,
//                    modifier = Modifier
//                        .animateContentSize()
//                        .padding(horizontal = 16.dp),
//                    maxLines = Int.MAX_VALUE,
//                    overflow = TextOverflow.Ellipsis
//                )
//                if (post.postImage2.isNotEmpty()) {
//                    PostImage(imageUrl = post.postImage2)
//                }
//                if (post.caption2.isNotEmpty()) {
//                    Text(
//                        text = post.caption2,
//                        modifier = Modifier
//                            .animateContentSize()
//                            .padding(horizontal = 16.dp),
//                        maxLines = Int.MAX_VALUE,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }
//        } else {
//            Text(
//                text = post.caption,
//                modifier = Modifier
//                    .animateContentSize()
//                    .padding(horizontal = 16.dp)
//                    .clickable {
//                        isExpanded = !isExpanded
//                    },
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
//        }
//    }
//}
//
//
//
//@Preview(showBackground = true)
//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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
