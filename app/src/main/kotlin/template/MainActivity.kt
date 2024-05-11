package template

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.core.designsystem.theme.TemplateTheme
import template.core.ui.component.Snackbar
import template.core.ui.extensions.message
import template.feature.launch.LaunchRoute
import template.feature.login.LoginRoute
import template.feature.postdetail.PostDetailRoute
import template.feature.posts.PostsRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        intent?.data?.let {
            viewModel.onEvent(MainUiEvent.Deeplink(it))
        }
        intent = null
    }

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var userAuth: UserAuthState by mutableStateOf(UserAuthState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userAuth
                    .onEach {
                        userAuth = it
                    }
                    .collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            userAuth is UserAuthState.Loading
        }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val deeplink by viewModel.deeplink.collectAsStateWithLifecycle()

            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            DisposableEffect(Unit) {
                val listener = Consumer<Intent> { intent ->
                    intent.data?.let {
                        viewModel.onEvent(MainUiEvent.Deeplink(it))
                        this@MainActivity.intent = null
                    }
                }
                addOnNewIntentListener(listener)
                onDispose { removeOnNewIntentListener(listener) }
            }

            LaunchedEffect(userAuth) {
                when (userAuth) {
                    UserAuthState.LoggedIn -> {
                        navController.navigate(NavigationGraph.Posts) { popUpTo(0) }
                    }

                    UserAuthState.LoggedOut -> {
                        navController.navigate(NavigationGraph.Login) { popUpTo(0) }
                    }

                    else -> {
                        // no-op
                    }
                }
            }

            LaunchedEffect(deeplink, userAuth) {
                deeplink?.let { deeplink ->
                    if (userAuth is UserAuthState.LoggedIn) {
                        navController.navigate(deeplink)
                        viewModel.onEvent(MainUiEvent.DeeplinkConsumed)
                    }
                }
            }

            Snackbar(
                snackbarHostState = snackbarHostState,
                message = uiState.error?.message(),
                onSnackbarShown = { viewModel.onEvent(MainUiEvent.ErrorConsumed) }
            )

            TemplateTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        content = { paddingValues ->
                            NavHost(
                                modifier = Modifier.padding(paddingValues),
                                navController = navController,
                                startDestination = Screen.Launch,
                            ) {
                                composable<Screen.Launch> {
                                    LaunchRoute()
                                }

                                navigation<NavigationGraph.Login>(
                                    startDestination = Screen.Login
                                ) {
                                    composable<Screen.Login> {
                                        LoginRoute(
                                            onError = { viewModel.onEvent(MainUiEvent.Error(it)) }
                                        )
                                    }
                                }

                                navigation<NavigationGraph.Posts>(
                                    startDestination = Screen.Posts,
                                ) {
                                    composable<Screen.Posts> {
                                        PostsRoute(
                                            onError = { viewModel.onEvent(MainUiEvent.Error(it)) },
                                            onPostClick = { postId ->
                                                navController.navigate(Screen.PostDetail(postId = postId))
                                            }
                                        )
                                    }

                                    composable<Screen.PostDetail>(
                                        deepLinks = listOf(
                                            navDeepLink {
                                                uriPattern = Screen.PostDetail.DeepLinkUriPattern
                                            }
                                        ),
                                    ) {
                                        PostDetailRoute(
                                            onBackClick = navController::navigateUp
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
