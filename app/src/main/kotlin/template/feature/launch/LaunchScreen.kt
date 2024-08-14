package template.feature.launch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import template.core.ui.component.AppProgressIndicator

@Composable
fun LaunchRoute() {
    LaunchScreen()
}

@Composable
fun LaunchScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AppProgressIndicator()
    }
}

@Preview
@Composable
private fun LaunchScreenPreview() {
    LaunchScreen()
}
