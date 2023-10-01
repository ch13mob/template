package com.ch13mob.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ch13mob.template.core.designsystem.component.ProgressIndicator

@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginButtonEnabled by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    onValueChange = {
                        email = it
                        isLoginButtonEnabled = email.isNotEmpty() && password.isNotEmpty()
                    },
                    enabled = true,
                    label = { Text(text = "Email") },
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = {
                        password = it
                        isLoginButtonEnabled = email.isNotEmpty() && password.isNotEmpty()
                    },
                    enabled = true,
                    label = { Text(text = "Password") },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        keyboardController?.hide()
                        onEvent(LoginUiEvent.Login(email, password))
                    },
                    enabled = isLoginButtonEnabled
                ) {
                    Text(text = "Login")
                }
            }

            if (uiState.isLoading) {
                ProgressIndicator()
            }
        }
    )
}
