package com.ch13mob.template

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ch13mob.template.core.designsystem.theme.TemplateTheme
import com.ch13mob.feature.login.navigation.loginScreen
import com.ch13mob.feature.postdetail.navigation.navigateToPostDetail
import com.ch13mob.feature.postdetail.navigation.postDetailScreen
import com.ch13mob.feature.posts.navigation.postsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        intent?.data?.let {
            viewModel.onEvent(MainUiEvent.HandleDeeplink(it))
        }
        intent = null
    }

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val appState by viewModel.appState.collectAsStateWithLifecycle()
            val navController = rememberNavController()

            DisposableEffect(Unit) {
                val listener = Consumer<Intent> { intent ->
                    intent.data?.let {
                        viewModel.onEvent(MainUiEvent.HandleDeeplink(it))
                    }
                    this@MainActivity.intent = null
                }
                addOnNewIntentListener(listener)
                onDispose { removeOnNewIntentListener(listener) }
            }

            LaunchedEffect(key1 = appState.isLoggedIn) {
                if (appState.isLoggedIn) {
                    navController.navigate(com.ch13mob.feature.posts.navigation.PostsNavigationGraph) {
                        popUpTo(0)
                    }
                } else {
                    navController.navigate(com.ch13mob.feature.login.navigation.LoginNavigationGraph) {
                        popUpTo(0)
                    }
                }
            }

            LaunchedEffect(key1 = appState.deeplinkUri != null && appState.isLoggedIn) {
                appState.deeplinkUri?.let {
                    navController.navigate(it)
                    viewModel.onEvent(MainUiEvent.DeeplinkConsumed)
                }
            }

            TemplateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = com.ch13mob.feature.posts.navigation.PostsNavigationGraph
                    ) {
                        navigation(
                            route = com.ch13mob.feature.login.navigation.LoginNavigationGraph,
                            startDestination = com.ch13mob.feature.login.navigation.LoginNavigationRoute,
                        ) {
                            loginScreen()
                        }

                        navigation(
                            route = com.ch13mob.feature.posts.navigation.PostsNavigationGraph,
                            startDestination = com.ch13mob.feature.posts.navigation.PostsNavigationRoute,
                        ) {
                            postsScreen(
                                onPostClick = navController::navigateToPostDetail
                            )
                            postDetailScreen(
                                onBackClick = navController::popBackStack
                            )
                        }
                    }
                }
            }
        }
    }
}