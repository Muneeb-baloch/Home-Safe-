package com.muneeb.homesafe.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onEditProfileClick: () -> Unit = {},
    onTrustedContactsClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileContent(
        uiState = uiState,
        onEditProfileClick = onEditProfileClick,
        onArrivalAlertsChange = viewModel::onArrivalAlertsChange,
        onOutingInvitesChange = viewModel::onOutingInvitesChange,
        onTrustedContactsClick = onTrustedContactsClick,
        onSignOutClick = onSignOutClick,
        modifier = modifier
    )
}
