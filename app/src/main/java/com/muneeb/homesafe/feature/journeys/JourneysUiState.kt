package com.muneeb.homesafe.feature.journeys

enum class JourneyFilter {
    ALL,
    WITH_COMPANIONS,
    SOLO
}

enum class JourneyIcon {
    NIGHT_OUT,
    DINING,
    MOVIE
}

data class JourneyItem(
    val id: String,
    val title: String,
    val dateLabel: String,
    val timeLabel: String,
    val companionsCount: Int,
    val icon: JourneyIcon,
    val statusLabel: String
)

data class JourneySection(
    val label: String,
    val journeys: List<JourneyItem>
)

data class JourneysUiState(
    val selectedFilter: JourneyFilter = JourneyFilter.ALL,
    val totalJourneysCount: Int = 14,
    val safeArrivalsCount: Int = 14,
    val sections: List<JourneySection> = listOf(
        JourneySection(
            label = "This week",
            journeys = listOf(
                JourneyItem(
                    id = "1",
                    title = "Friday Night Out",
                    dateLabel = "Oct 3",
                    timeLabel = "11:42 PM",
                    companionsCount = 3,
                    icon = JourneyIcon.NIGHT_OUT,
                    statusLabel = "Arrived"
                ),
                JourneyItem(
                    id = "2",
                    title = "Dinner & Drinks",
                    dateLabel = "Oct 2",
                    timeLabel = "10:51 PM",
                    companionsCount = 2,
                    icon = JourneyIcon.DINING,
                    statusLabel = "Arrived"
                )
            )
        ),
        JourneySection(
            label = "Last week",
            journeys = listOf(
                JourneyItem(
                    id = "3",
                    title = "Movie Night",
                    dateLabel = "Sep 28",
                    timeLabel = "11:18 PM",
                    companionsCount = 2,
                    icon = JourneyIcon.MOVIE,
                    statusLabel = "Arrived"
                ),
                JourneyItem(
                    id = "4",
                    title = "Late Dinner",
                    dateLabel = "Sep 26",
                    timeLabel = "12:07 AM",
                    companionsCount = 0,
                    icon = JourneyIcon.DINING,
                    statusLabel = "Arrived"
                )
            )
        )
    )
)
