package com.muneeb.homesafe.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val uiState by remember { mutableStateOf(HomeUiState()) }

    HomeContent(
        uiState = uiState,
        onStartJourneyClick = { /* Navigate or start journey action */ },
        modifier = modifier
    )
}