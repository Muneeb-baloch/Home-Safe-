package com.muneeb.homesafe.feature.journeys

enum class JourneyStepType {
    OUTING_STARTED,
    LEFT_HOME,
    HEADING_HOME,
    ARRIVED_HOME
}

data class JourneyTimelineStep(
    val type: JourneyStepType,
    val time: String,
    val isFinal: Boolean = false
)

data class NotifiedCompanion(
    val initial: String,
    val name: String,
    val email: String
)

data class JourneyDetailsUiState(
    val title: String,
    val dateTimeLabel: String,
    val arrivalTimeLabel: String,
    val timeline: List<JourneyTimelineStep>,
    val companions: List<NotifiedCompanion>
)

object JourneyDetailsSampleData {
    private val byId = mapOf(
        "1" to JourneyDetailsUiState(
            title = "Friday Night Out",
            dateTimeLabel = "Friday, Oct 3 · 11:42 PM",
            arrivalTimeLabel = "11:42 PM",
            timeline = listOf(
                JourneyTimelineStep(JourneyStepType.OUTING_STARTED, "7:30 PM"),
                JourneyTimelineStep(JourneyStepType.LEFT_HOME, "8:05 PM"),
                JourneyTimelineStep(JourneyStepType.HEADING_HOME, "11:20 PM"),
                JourneyTimelineStep(JourneyStepType.ARRIVED_HOME, "11:42 PM", isFinal = true)
            ),
            companions = listOf(
                NotifiedCompanion("A", "Ahmed", "ahmed@example.com"),
                NotifiedCompanion("A", "Ali", "ali@example.com"),
                NotifiedCompanion("H", "Hamza", "hamza@example.com")
            )
        ),
        "2" to JourneyDetailsUiState(
            title = "Dinner & Drinks",
            dateTimeLabel = "Thursday, Oct 2 · 10:51 PM",
            arrivalTimeLabel = "10:51 PM",
            timeline = listOf(
                JourneyTimelineStep(JourneyStepType.OUTING_STARTED, "7:50 PM"),
                JourneyTimelineStep(JourneyStepType.LEFT_HOME, "8:15 PM"),
                JourneyTimelineStep(JourneyStepType.HEADING_HOME, "10:30 PM"),
                JourneyTimelineStep(JourneyStepType.ARRIVED_HOME, "10:51 PM", isFinal = true)
            ),
            companions = listOf(
                NotifiedCompanion("A", "Ayesha", "ayesha@example.com"),
                NotifiedCompanion("B", "Bilal", "bilal@example.com")
            )
        ),
        "3" to JourneyDetailsUiState(
            title = "Movie Night",
            dateTimeLabel = "Monday, Sep 28 · 11:18 PM",
            arrivalTimeLabel = "11:18 PM",
            timeline = listOf(
                JourneyTimelineStep(JourneyStepType.OUTING_STARTED, "8:40 PM"),
                JourneyTimelineStep(JourneyStepType.LEFT_HOME, "9:00 PM"),
                JourneyTimelineStep(JourneyStepType.HEADING_HOME, "10:55 PM"),
                JourneyTimelineStep(JourneyStepType.ARRIVED_HOME, "11:18 PM", isFinal = true)
            ),
            companions = listOf(
                NotifiedCompanion("A", "Ali", "ali@example.com"),
                NotifiedCompanion("H", "Hamza", "hamza@example.com")
            )
        ),
        "4" to JourneyDetailsUiState(
            title = "Late Dinner",
            dateTimeLabel = "Sunday, Sep 26 · 12:07 AM",
            arrivalTimeLabel = "12:07 AM",
            timeline = listOf(
                JourneyTimelineStep(JourneyStepType.OUTING_STARTED, "9:30 PM"),
                JourneyTimelineStep(JourneyStepType.LEFT_HOME, "9:45 PM"),
                JourneyTimelineStep(JourneyStepType.HEADING_HOME, "11:50 PM"),
                JourneyTimelineStep(JourneyStepType.ARRIVED_HOME, "12:07 AM", isFinal = true)
            ),
            companions = emptyList()
        )
    )

    fun forId(journeyId: String?): JourneyDetailsUiState =
        byId[journeyId] ?: byId.getValue("1")
}
