package com.muneeb.homesafe.navigation

import androidx.annotation.DrawableRes
import com.muneeb.homesafe.R

enum class TopLevelDestination(
    val route: String,
    @DrawableRes val iconRes: Int,
    val title: String
) {
    HOME(
        route = "home",
        iconRes = R.drawable.ic_home_icon,
        title = "Home"
    ),
    JOURNEYS(
        route = "journeys",
        iconRes = R.drawable.ic_journey_icon,
        title = "Journeys"
    ),
    ALERTS(
        route = "alerts",
        iconRes = R.drawable.ic_notification_icon,
        title = "Alerts"
    ),
    PROFILE(
        route = "profile",
        iconRes = R.drawable.ic_profile_icon,
        title = "Profile"
    )
}