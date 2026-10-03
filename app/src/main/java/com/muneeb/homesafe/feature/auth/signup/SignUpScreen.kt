package com.muneeb.homesafe.feature.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SignUpScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onNavigateToPrivacy: () -> Unit
) {
    var uiState by remember { mutableStateOf(SignUpUiState()) }

    SignUpContent(
        uiState = uiState,
        onFullNameChange = { uiState = uiState.copy(fullName = it) },
        onEmailChange = { uiState = uiState.copy(email = it) },
        onPasswordChange = { uiState = uiState.copy(password = it) },
        onConfirmPasswordChange = { uiState = uiState.copy(confirmPassword = it) },
        onPasswordVisibilityToggle = {
            uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
        },
        onConfirmPasswordVisibilityToggle = {
            uiState = uiState.copy(isConfirmPasswordVisible = !uiState.isConfirmPasswordVisible)
        },
        onBackClick = onNavigateBack,
        onCreateAccountClick = {

        },
        onTermsClick = onNavigateToTerms,
        onPrivacyClick = onNavigateToPrivacy,
        onSignInClick = onNavigateToSignIn
    )
}