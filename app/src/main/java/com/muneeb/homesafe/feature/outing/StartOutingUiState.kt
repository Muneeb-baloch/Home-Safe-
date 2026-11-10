package com.muneeb.homesafe.feature.outing

data class StartOutingUiState(
    val isStarting: Boolean = false,
    val createdOutingId: String? = null,
    val error: String? = null
)
