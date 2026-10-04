package com.muneeb.homesafe.feature.journeys

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun JourneysScreen(
    modifier: Modifier = Modifier
) {
    val uiState by remember { mutableStateOf(JourneysUiState()) }

    JourneysContent(
        uiState = uiState,
        modifier = modifier
    )
}