package template

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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import template.core.designsystem.theme.TemplateTheme
import template.core.ui.LocalSnackbarHostState

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
            val deeplink by viewModel.deeplink.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            TemplateTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                        Scaffold(
                            snackbarHost = { SnackbarHost(snackbarHostState) },
                            content = { paddingValues ->
                                AppNavHost(
                                    modifier = Modifier.padding(paddingValues),
                                    userAuth = userAuth,
                                    deeplink = deeplink,
                                    onDeeplinkConsumed = { viewModel.onEvent(MainUiEvent.DeeplinkConsumed) }
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
