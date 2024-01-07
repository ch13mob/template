package template

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.core.designsystem.theme.TemplateTheme
import template.core.ui.component.Snackbar
import template.core.ui.extensions.message
import template.feature.login.navigation.LoginNavigationGraph
import template.feature.login.navigation.LoginNavigationRoute
import template.feature.login.navigation.loginScreen
import template.feature.postdetail.navigation.navigateToPostDetail
import template.feature.postdetail.navigation.postDetailScreen
import template.feature.posts.navigation.PostsNavigationGraph
import template.feature.posts.navigation.PostsNavigationRoute
import template.feature.posts.navigation.postsScreen

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
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var userAuth: UserAuthState by mutableStateOf(UserAuthState(isLoading = true))

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
            userAuth.isLoading
        }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val deeplink by viewModel.deeplink.collectAsStateWithLifecycle()

            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            DisposableEffect(Unit) {
                val listener = Consumer<Intent> { intent ->
                    intent.data?.let {
                        viewModel.onEvent(MainUiEvent.HandleDeeplink(it))
                        this@MainActivity.intent = null
                    }
                }
                addOnNewIntentListener(listener)
                onDispose { removeOnNewIntentListener(listener) }
            }

            LaunchedEffect(userAuth.isLoggedIn) {
                val route = if (userAuth.isLoggedIn) {
                    PostsNavigationGraph
                } else {
                    LoginNavigationGraph
                }

                navController.navigate(route) { popUpTo(0) }
            }

            LaunchedEffect(deeplink != null && userAuth.isLoggedIn) {
                deeplink?.let { deeplink ->
                    if (userAuth.isLoggedIn) {
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
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                navController = navController,
                                enterTransition = { EnterTransition.None },
                                exitTransition = { ExitTransition.None },
                                startDestination = PostsNavigationGraph
                            ) {
                                navigation(
                                    route = LoginNavigationGraph,
                                    startDestination = LoginNavigationRoute,
                                ) {
                                    loginScreen(
                                        onError = { viewModel.onEvent(MainUiEvent.HandleError(it)) }
                                    )
                                }

                                navigation(
                                    route = PostsNavigationGraph,
                                    startDestination = PostsNavigationRoute,
                                ) {
                                    postsScreen(
                                        onError = { viewModel.onEvent(MainUiEvent.HandleError(it)) },
                                        onPostClick = navController::navigateToPostDetail
                                    )
                                    postDetailScreen(
                                        onBackClick = navController::popBackStack
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
