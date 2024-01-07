package template.core.ui.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Snackbar(
    snackbarHostState: SnackbarHostState,
    message: String?,
    duration: SnackbarDuration = SnackbarDuration.Short,
    onSnackbarShown: () -> Unit = {}
) {
    LaunchedEffect(message.isNullOrEmpty().not()) {
        message?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = duration
            )
            onSnackbarShown()
        }
    }
}
