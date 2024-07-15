package com.example.wirein

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wirein.ui.theme.JetPackComposeBasicTheme
import com.example.wirein.ui.theme.Navigation.NavigationItem
import com.example.wirein.ui.theme.Navigation.Screen
import com.example.wirein.ui.theme.Navigation.navHost.AppNavHost
import com.example.wirein.ui.theme.components.BottomIcon
import com.example.wirein.ui.theme.components.BottomNavItem
import com.example.wirein.ui.theme.components.BottomNavigationBar
import com.example.wirein.ui.theme.components.CircularImage
import com.example.wirein.ui.theme.screen.viewmodel.HomeViewModel
import com.example.wirein.util.AppConstants.Companion.MY_USER_ID
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposeBasicTheme {
                SetupTransparentSystemUi(
                    systemUiController = rememberSystemUiController(),
                    actualBackgroundColor = MaterialTheme.colorScheme.surface
                )
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val screens = listOf(
                        NavigationItem.Home.route,
                        NavigationItem.Search.route,
                        NavigationItem.CreatePost.route,
                        NavigationItem.TweetList.route,
                        "${NavigationItem.Profile.route}/{userid}"
                    )
                    val showBottomBar = navController
                        .currentBackStackEntryAsState().value?.destination?.route in screens.map { it }
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = showBottomBar,
                                enter = fadeIn() + scaleIn(),
                                exit = fadeOut() + scaleOut(),
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .fillMaxWidth()
                                ) {
                                    BottomNavigationBar(
                                        items = listOf(
                                            BottomNavItem(
                                                NavigationItem.Home.route,
                                                Screen.HOME.name,
                                                icon = painterResource(id = R.drawable.icons8_newspaper_48)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Search.route,
                                                Screen.SEARCH.name,
                                                icon = rememberVectorPainter(image = Icons.Default.Search)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.CreatePost.route,
                                                Screen.CREATE_POST.name,
                                                icon = rememberVectorPainter(image = Icons.Default.AddCircle)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Reels.route,
                                                Screen.TWEET_LIST.name,
                                                icon = painterResource(id = R.drawable.node_iconn)
                                            ),
                                            BottomNavItem(
                                                NavigationItem.Profile.route,
                                                Screen.PROFILE.name,
                                                icon = rememberVectorPainter(image = Icons.Default.Person)
                                            ),
                                        ),
                                        navController = navController,
                                        onItemClick = {
                                            if (it.route == NavigationItem.Profile.route) {
                                                navController.navigate(
                                                    "${NavigationItem.Profile.route}/$MY_USER_ID"
                                                )
                                            } else {
                                                navController.navigate(it.route)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    ) {
                        AppNavHost(
                            homeViewModel = homeViewModel,
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun SetupTransparentSystemUi(
    systemUiController: SystemUiController = rememberSystemUiController(),
    actualBackgroundColor: Color,
) {
    val minLuminanceForDarkIcons = .5f

    SideEffect {
        systemUiController.setStatusBarColor(
            color = actualBackgroundColor,
            darkIcons = actualBackgroundColor.luminance() > minLuminanceForDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = actualBackgroundColor,
            darkIcons = actualBackgroundColor.luminance() > minLuminanceForDarkIcons,
            navigationBarContrastEnforced = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetPackComposeBasicTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            val homeViewModel: HomeViewModel = viewModel()

            Scaffold(
                bottomBar = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        BottomIcon(
                            imageVector = Icons.Default.Home,
                        ) {
                            navController.navigate(Screen.HOME.name)
                        }
                        BottomIcon(
                            imageVector = Icons.Default.Search
                        ) {
                            navController.navigate(NavigationItem.Search.route)
                        }
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,

                            )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                        )
                        CircularImage(
                            imageUrl = "https://cdn.pixabay.com/photo/2023/05/23/15/26/bengal-cat-8012976_1280.jpg",
                            imageSize = 24.dp,
                            isBorderVisible = false,
                            isNameVisible = false,
                            isAnimated = false
                        )
                    }
                }
            ) {

                AppNavHost(homeViewModel, navController, Modifier.padding(it))
            }
        }
    }
}