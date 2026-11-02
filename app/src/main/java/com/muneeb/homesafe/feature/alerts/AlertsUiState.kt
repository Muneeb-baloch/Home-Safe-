package com.muneeb.homesafe.feature.alerts

enum class AlertType {
    ARRIVAL,
    INVITE
}

data class AlertItem(
    val id: String,
    val type: AlertType,
    val title: String,
    val meta: String,
    val timeLabel: String,
    val isUnread: Boolean = false
)

data class AlertSection(
    val label: String,
    val alerts: List<AlertItem>
)

data class AlertsUiState(
    val sections: List<AlertSection> = listOf(
        AlertSection(
            label = "New",
            alerts = listOf(
                AlertItem(
                    id = "1",
                    type = AlertType.ARRIVAL,
                    title = "Ahmed reached home safely",
                    meta = "Friday Night Out · 11:42 PM",
                    timeLabel = "now",
                    isUnread = true
                ),
                AlertItem(
                    id = "2",
                    type = AlertType.ARRIVAL,
                    title = "Ali reached home safely",
                    meta = "Friday Night Out · 11:58 PM",
                    timeLabel = "now",
                    isUnread = true
                )
            )
        ),
        AlertSection(
            label = "Earlier",
            alerts = listOf(
                AlertItem(
                    id = "3",
                    type = AlertType.INVITE,
                    title = "Ayesha invited you to Dinner & Drinks",
                    meta = "Outing invite",
                    timeLabel = "2h ago"
                ),
                AlertItem(
                    id = "4",
                    type = AlertType.ARRIVAL,
                    title = "Bilal reached home safely",
                    meta = "Movie Night · 11:18 PM",
                    timeLabel = "Yesterday"
                )
            )
        )
    )
)
