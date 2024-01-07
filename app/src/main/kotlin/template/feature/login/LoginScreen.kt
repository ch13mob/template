package template.feature.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import template.core.common.error.Error
import template.core.ui.component.ProgressIndicator

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onError: (Error) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        email = viewModel.email,
        password = viewModel.password,
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onError = onError
    )
}

@Suppress("LongMethod")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    email: String,
    password: String,
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    onError: (Error) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailError by remember(uiState.isEmailValid) {
        mutableStateOf(
            if (uiState.isEmailValid) "" else "Email should contain min 5 characters"
        )
    }
    val passwordError by remember(uiState.isPasswordValid) {
        mutableStateOf(
            if (uiState.isPasswordValid) "" else "Password should contain min 5 characters"
        )
    }

    LaunchedEffect(uiState.error != null) {
        uiState.error?.let { onError(it) }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email ->
                onEvent(LoginUiEvent.EmailChanged(email))
            },
            enabled = true,
            label = { Text(text = "Email") },
            isError = !uiState.isEmailValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = emailError,
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password ->
                onEvent(LoginUiEvent.PasswordChanged(password))
            },
            enabled = true,
            label = { Text(text = "Password") },
            isError = !uiState.isPasswordValid
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = passwordError,
            textAlign = TextAlign.Start,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(36.dp))
        Button(
            onClick = {
                keyboardController?.hide()
                onEvent(LoginUiEvent.Login(email, password))
            },
            enabled = uiState.isLoginButtonEnabled
        ) {
            Text(text = "Login")
        }
    }

    AnimatedVisibility(
        visible = uiState.isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        ProgressIndicator()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        email = "",
        password = "",
        uiState = LoginUiState(),
        onEvent = {},
        onError = {}
    )
}
