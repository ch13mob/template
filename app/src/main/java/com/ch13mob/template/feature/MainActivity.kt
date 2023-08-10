package com.ch13mob.template.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ch13mob.template.core.designsystem.theme.TemplateTheme
import com.ch13mob.template.feature.qoutedetail.navigation.navigateToQuoteDetail
import com.ch13mob.template.feature.qoutedetail.navigation.quoteDetailScreen
import com.ch13mob.template.feature.quotes.navigation.QuotesNavigationRoute
import com.ch13mob.template.feature.quotes.navigation.quotesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            TemplateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = QuotesNavigationRoute
                    ) {
                        quotesScreen(
                            onQuoteClick = { quoteId, quoteText ->
                                navController.navigateToQuoteDetail(quoteId, quoteText)
                            }
                        )
                        quoteDetailScreen(
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
