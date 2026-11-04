package com.muneeb.homesafe.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onStartOutingClick: () -> Unit = {},
    onScanQrClick: () -> Unit = {},
    onEnterCodeClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onViewAllJourneysClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by remember { mutableStateOf(HomeUiState()) }

    HomeContent(
        uiState = uiState,
        onStartJourneyClick = onStartOutingClick,
        onScanQrClick = onScanQrClick,
        onEnterCodeClick = onEnterCodeClick,
        onChangeHomeLocationClick = { /* Navigate to home location picker */ },
        onViewAllJourneysClick = onViewAllJourneysClick,
        onNotificationsClick = onNotificationsClick,
        onProfileClick = onProfileClick,
        modifier = modifier
    )
}
