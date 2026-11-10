package com.muneeb.homesafe.feature.join

data class JoinConfirmationUiState(
    val invitedByName: String = "",
    val memberNames: List<String> = emptyList(),
    val startedAtLabel: String = "",
    val isJoining: Boolean = false,
    val error: Boolean = false,
    val joined: Boolean = false
)
