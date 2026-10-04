package com.muneeb.homesafe.feature.journeys

data class JourneyItem(val title: String, val time: String, val status: String)

data class JourneysUiState(
    val journeys: List<JourneyItem> = listOf(
        JourneyItem("Walk to Gym", "Today, 7:30 AM", "Completed"),
        JourneyItem("Drive to Office", "Yesterday, 8:45 AM", "Completed"),
        JourneyItem("Late Night Ride Home", "Oct 1, 11:20 PM", "Completed")
    ),
    val isLoading: Boolean = false
)