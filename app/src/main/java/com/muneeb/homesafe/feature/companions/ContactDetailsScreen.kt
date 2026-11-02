package com.muneeb.homesafe.feature.companions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContactDetailsScreen(
    contactId: String?,
    onBackClick: () -> Unit,
    onSharedOutingClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    ContactDetailsContent(
        uiState = ContactDetailsSampleData.forId(contactId),
        onBackClick = onBackClick,
        onSharedOutingClick = onSharedOutingClick,
        modifier = modifier
    )
}
