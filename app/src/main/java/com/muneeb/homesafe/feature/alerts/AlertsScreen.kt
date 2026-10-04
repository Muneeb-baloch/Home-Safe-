package com.muneeb.homesafe.feature.alerts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AlertsScreen(
    modifier: Modifier = Modifier
) {
    val uiState by remember { mutableStateOf(AlertsUiState()) }

    AlertsContent(
        uiState = uiState,
        modifier = modifier
    )
}