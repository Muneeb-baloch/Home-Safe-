package com.muneeb.homesafe.feature.journeys

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun JourneysScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onJourneyClick: (JourneyItem) -> Unit = {}
) {
    var uiState by remember { mutableStateOf(JourneysUiState()) }

    JourneysContent(
        uiState = uiState,
        onFilterSelect = { filter -> uiState = uiState.copy(selectedFilter = filter) },
        onSearchClick = onSearchClick,
        onProfileClick = onProfileClick,
        onJourneyClick = onJourneyClick,
        modifier = modifier
    )
}
