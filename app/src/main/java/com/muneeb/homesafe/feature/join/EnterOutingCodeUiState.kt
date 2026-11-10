package com.muneeb.homesafe.feature.join

data class EnterOutingCodeUiState(
    val code: String = "",
    val isSearching: Boolean = false,
    val showError: Boolean = false,
    val foundOutingId: String? = null
)
