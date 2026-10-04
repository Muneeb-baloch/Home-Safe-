package com.muneeb.homesafe.feature.alerts

data class AlertsUiState(
    val hasActiveAlerts: Boolean = false,
    val alertsCount: Int = 0
)