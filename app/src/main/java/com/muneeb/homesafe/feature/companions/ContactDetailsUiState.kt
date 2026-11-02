package com.muneeb.homesafe.feature.companions

enum class SharedOutingIcon {
    NIGHT_OUT,
    DINING,
    MOVIE
}

data class SharedOuting(
    val journeyId: String,
    val title: String,
    val dateLabel: String,
    val icon: SharedOutingIcon
)

data class ContactDetailsUiState(
    val name: String,
    val phone: String,
    val isTrusted: Boolean,
    val outingsCount: Int,
    val lastOutingLabel: String,
    val avatarStyle: AvatarStyle,
    val sharedOutings: List<SharedOuting>
)

object ContactDetailsSampleData {
    private val byId = mapOf(
        "ahmed" to ContactDetailsUiState(
            name = "Ahmed Khan",
            phone = "+92 300 1234567",
            isTrusted = true,
            outingsCount = 4,
            lastOutingLabel = "Oct 3",
            avatarStyle = AvatarStyle.SOLID,
            sharedOutings = listOf(
                SharedOuting("1", "Friday Night Out", "Oct 3", SharedOutingIcon.NIGHT_OUT),
                SharedOuting("2", "Dinner & Drinks", "Oct 2", SharedOutingIcon.DINING)
            )
        ),
        "ali" to ContactDetailsUiState(
            name = "Ali Raza",
            phone = "+92 321 9034567",
            isTrusted = true,
            outingsCount = 3,
            lastOutingLabel = "Oct 2",
            avatarStyle = AvatarStyle.TINT,
            sharedOutings = listOf(
                SharedOuting("2", "Dinner & Drinks", "Oct 2", SharedOutingIcon.DINING),
                SharedOuting("3", "Movie Night", "Sep 28", SharedOutingIcon.MOVIE)
            )
        ),
        "sara" to ContactDetailsUiState(
            name = "Sara Malik",
            phone = "+92 333 1122334",
            isTrusted = true,
            outingsCount = 1,
            lastOutingLabel = "Sep 28",
            avatarStyle = AvatarStyle.TINT,
            sharedOutings = listOf(
                SharedOuting("3", "Movie Night", "Sep 28", SharedOutingIcon.MOVIE)
            )
        ),
        "hamza" to ContactDetailsUiState(
            name = "Hamza Iqbal",
            phone = "+92 345 5566778",
            isTrusted = false,
            outingsCount = 1,
            lastOutingLabel = "Sep 28",
            avatarStyle = AvatarStyle.TINT,
            sharedOutings = listOf(
                SharedOuting("3", "Movie Night", "Sep 28", SharedOutingIcon.MOVIE)
            )
        )
    )

    fun forId(contactId: String?): ContactDetailsUiState =
        byId[contactId] ?: byId.getValue("ahmed")
}
