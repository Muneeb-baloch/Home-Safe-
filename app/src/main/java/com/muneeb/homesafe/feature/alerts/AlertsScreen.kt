package com.muneeb.homesafe.feature.alerts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AlertsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AlertsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    AlertsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
