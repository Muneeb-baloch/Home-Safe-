package com.muneeb.homesafe.feature.home

data class HomeUiState(
    val userName: String = "Alex",
    val isProtected: Boolean = true,
    val isLoading: Boolean = false
)