package com.muneeb.homesaffe.feature.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    // Local state setup (or collect from ViewModel: val uiState by viewModel.uiState.collectAsStateWithLifecycle())
    var uiState by remember { mutableStateOf(LoginUiState()) }

    LoginContent(
        uiState = uiState,
        onEmailChange = { newEmail ->
            uiState = uiState.copy(email = newEmail)
        },
        onPasswordChange = { newPassword ->
            uiState = uiState.copy(password = newPassword)
        },
        onPasswordVisibilityToggle = {
            uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
        },
        onSignInClick = {
            // Trigger login action
            onNavigateToHome()
        },
        onForgotPasswordClick = onNavigateToForgotPassword,
        onGoogleSignInClick = {
            // Trigger Google auth flow
        },
        onCreateAccountClick = onNavigateToSignUp
    )
}