package template.navigation

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import template.core.ui.extensions.findActivity

@Composable
fun rememberDeeplinkState(deeplink: Uri?) = remember(deeplink) {
    mutableStateOf(deeplink)
}

@Composable
fun AppDeeplinkConsumer(
    isLoggedIn: Boolean,
    onDeeplinkConsume: (Uri) -> Unit,
) {
    var deeplink by rememberDeeplinkState(null)

    DeeplinkObserver(
        onNewDeeplink = { deeplink = it }
    )

    LaunchedEffect(deeplink, isLoggedIn) {
        deeplink?.let {
            if (isLoggedIn) {
                onDeeplinkConsume(it)
                deeplink = null
            }
        }
    }
}

@Composable
private fun DeeplinkObserver(
    onNewDeeplink: (Uri?) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Cold start
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    context.findActivity()?.intent?.data?.let {
                        onNewDeeplink(it)
                    }

                    context.findActivity()?.intent = null
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Hot start
    val componentActivity = (context.findActivity() as ComponentActivity)
    DisposableEffect(Unit) {
        val listener = Consumer<Intent> { intent ->
            intent.data?.let {
                onNewDeeplink(it)
            }

            context.findActivity()?.intent = null
        }

        componentActivity.addOnNewIntentListener(listener)
        onDispose {
            componentActivity.removeOnNewIntentListener(listener)
        }
    }
}
