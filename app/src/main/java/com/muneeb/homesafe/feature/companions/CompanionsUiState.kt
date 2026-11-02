package com.muneeb.homesafe.feature.companions

enum class CompanionsFilter {
    TRUSTED,
    RECENT
}

enum class AvatarStyle {
    SOLID,
    TINT
}

data class CompanionSummary(
    val id: String,
    val initial: String,
    val name: String,
    val outingsCount: Int,
    val lastOutingLabel: String,
    val avatarStyle: AvatarStyle = AvatarStyle.TINT
)

data class RecentCompanionSuggestion(
    val id: String,
    val initial: String,
    val name: String,
    val joinedOutingLabel: String
)

data class CompanionsUiState(
    val selectedFilter: CompanionsFilter = CompanionsFilter.TRUSTED,
    val trustedCompanions: List<CompanionSummary> = listOf(
        CompanionSummary(
            id = "ahmed",
            initial = "A",
            name = "Ahmed Khan",
            outingsCount = 4,
            lastOutingLabel = "Oct 3",
            avatarStyle = AvatarStyle.SOLID
        ),
        CompanionSummary(
            id = "ali",
            initial = "A",
            name = "Ali Raza",
            outingsCount = 3,
            lastOutingLabel = "Oct 2"
        ),
        CompanionSummary(
            id = "sara",
            initial = "S",
            name = "Sara Malik",
            outingsCount = 1,
            lastOutingLabel = "Sep 28"
        )
    ),
    val recentCompanions: List<CompanionSummary> = listOf(
        CompanionSummary(
            id = "hamza",
            initial = "H",
            name = "Hamza Iqbal",
            outingsCount = 1,
            lastOutingLabel = "Sep 28"
        )
    ),
    val recentSuggestions: List<RecentCompanionSuggestion> = listOf(
        RecentCompanionSuggestion(
            id = "hamza",
            initial = "H",
            name = "Hamza Iqbal",
            joinedOutingLabel = "Movie Night"
        )
    )
)
