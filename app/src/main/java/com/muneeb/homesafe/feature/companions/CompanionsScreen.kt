package com.muneeb.homesafe.feature.companions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CompanionsScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onAddTrustedContactClick: () -> Unit = {},
    onCompanionClick: (String) -> Unit = {}
) {
    var uiState by remember { mutableStateOf(CompanionsUiState()) }

    CompanionsContent(
        uiState = uiState,
        onFilterSelect = { filter -> uiState = uiState.copy(selectedFilter = filter) },
        onSearchClick = onSearchClick,
        onAddTrustedContactClick = onAddTrustedContactClick,
        onCompanionClick = onCompanionClick,
        onTrustClick = { /* Promote to trusted contact */ },
        modifier = modifier
    )
}
