package com.muneeb.homesafe.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit = {},
    onTrustedContactsClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {}
) {
    var uiState by remember { mutableStateOf(ProfileUiState()) }

    ProfileContent(
        uiState = uiState,
        onEditProfileClick = onEditProfileClick,
        onArrivalAlertsChange = { enabled -> uiState = uiState.copy(arrivalAlertsEnabled = enabled) },
        onOutingInvitesChange = { enabled -> uiState = uiState.copy(outingInvitesEnabled = enabled) },
        onTrustedContactsClick = onTrustedContactsClick,
        onSignOutClick = onSignOutClick,
        modifier = modifier
    )
}
