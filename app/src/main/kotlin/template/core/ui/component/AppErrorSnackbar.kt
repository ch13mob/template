package template.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import template.core.common.error.Error
import template.core.ui.LocalSnackbarHostState
import template.core.ui.extensions.message

@Composable
fun AppErrorSnackbar(
    error: Error?,
    onErrorConsumed: () -> Unit
) {
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(message = it.message())
            onErrorConsumed()
        }
    }
}
