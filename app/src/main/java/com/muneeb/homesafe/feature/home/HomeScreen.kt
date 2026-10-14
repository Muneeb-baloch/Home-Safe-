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
        onStartJourneyClick = { /* Navigate to start-an-outing flow */ },
        onScanQrClick = { /* Navigate to QR scanner */ },
        onEnterCodeClick = { /* Navigate to enter-code flow */ },
        onChangeHomeLocationClick = { /* Navigate to home location picker */ },
        onViewAllJourneysClick = { /* Navigate to journeys list */ },
        onNotificationsClick = { /* Navigate to alerts */ },
        onProfileClick = { /* Navigate to profile */ },
        modifier = modifier
    )
}
