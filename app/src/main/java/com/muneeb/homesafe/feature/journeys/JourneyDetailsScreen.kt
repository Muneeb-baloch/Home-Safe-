package com.muneeb.homesafe.feature.journeys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun JourneyDetailsScreen(
    journeyId: String?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    JourneyDetailsContent(
        uiState = JourneyDetailsSampleData.forId(journeyId),
        onBackClick = onBackClick,
        modifier = modifier
    )
}
