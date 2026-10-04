package com.muneeb.homesafe.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val uiState by remember { mutableStateOf(ProfileUiState()) }

    ProfileContent(
        uiState = uiState,
        onOptionClick = { /* Handle setting item click */ },
        modifier = modifier
    )
}