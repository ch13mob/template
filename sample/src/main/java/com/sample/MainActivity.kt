package com.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.features.FeaturesScreen
import com.sample.features.animatedcounter.AnimatedCounterScreen
import com.sample.features.annotatedstring.AnnotatedStringScreen
import com.sample.features.carousel.CarouselScreen
import com.sample.features.marqueetext.MarqueeTextScreen
import com.sample.features.photopicker.PhotoPickerScreen
import com.sample.features.scanner.DocumentScannerScreen
import com.sample.features.tabrow.TabRowHorizontalPagerScreen
import com.sample.features.textselection.TextSelectionScreen
import com.sample.features.webview.WebViewScreen
import com.sample.ui.theme.SampleTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Suppress("LongMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            var showBackButton by remember { mutableStateOf(false) }
            val navController = rememberNavController().apply {
                addOnDestinationChangedListener { _, destination, _ ->
                    showBackButton = !destination.hasRoute(Screen.Features::class)
                }
            }

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            SampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(text = stringResource(id = R.string.label_samples)) },
                                scrollBehavior = scrollBehavior,
                                navigationIcon = {
                                    if (showBackButton) {
                                        IconButton(onClick = { navController.navigateUp() }) {
                                            Icon(
                                                Icons.AutoMirrored.Default.ArrowBack,
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                }
                            )
                        },
                        content = { paddingValues ->
                            NavHost(
                                modifier = Modifier.padding(paddingValues),
                                navController = navController,
                                startDestination = Screen.Features
                            ) {
                                composable<Screen.Features> {
                                    FeaturesScreen(
                                        features = uiState.features,
                                        onFeatureClick = { route ->
                                            navController.navigate(route)
                                        }
                                    )
                                }

                                composable<Screen.AnimatedCounter> {
                                    AnimatedCounterScreen()
                                }
                                composable<Screen.AnnotatedString> {
                                    AnnotatedStringScreen()
                                }
                                composable<Screen.PhotoPicker> {
                                    PhotoPickerScreen()
                                }
                                composable<Screen.TextSelection> {
                                    TextSelectionScreen()
                                }
                                composable<Screen.MarqueeText> {
                                    MarqueeTextScreen()
                                }
                                composable<Screen.WebView> {
                                    WebViewScreen()
                                }
                                composable<Screen.TabRowHorizontalPager> {
                                    TabRowHorizontalPagerScreen()
                                }
                                composable<Screen.DocumentScanner> {
                                    DocumentScannerScreen()
                                }
                                composable<Screen.Carousel> {
                                    CarouselScreen()
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
