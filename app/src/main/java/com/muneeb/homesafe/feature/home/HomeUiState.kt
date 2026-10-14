package com.muneeb.homesafe.feature.home

data class HomeUiState(
    val userName: String = "Muneeb",
    val hasUnreadNotifications: Boolean = true,
    val isHomeZoneActive: Boolean = true,
    val hasActiveOuting: Boolean = false,
    val homeLocationLabel: String = "Home",
    val geofenceLabel: String = "150m geofence",
    val recentJourneys: List<RecentJourney> = defaultRecentJourneys(),
    val isLoading: Boolean = false
)

data class RecentJourney(
    val id: String,
    val title: String,
    val dateLabel: String,
    val companionsLabel: String,
    val statusLabel: String,
    val icon: JourneyIcon
)

enum class JourneyIcon {
    NIGHT_OUT,
    DINING
}

private fun defaultRecentJourneys(): List<RecentJourney> = listOf(
    RecentJourney(
        id = "1",
        title = "Friday Night Out",
        dateLabel = "Oct 3 · 11:42 PM",
        companionsLabel = "3 companions",
        statusLabel = "Arrived",
        icon = JourneyIcon.NIGHT_OUT
    ),
    RecentJourney(
        id = "2",
        title = "Dinner & Drinks",
        dateLabel = "Oct 2 · 10:51 PM",
        companionsLabel = "2 companions",
        statusLabel = "Arrived",
        icon = JourneyIcon.DINING
    )
)
