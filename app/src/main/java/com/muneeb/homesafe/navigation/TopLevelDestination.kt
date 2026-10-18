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
        iconRes = R.drawable.ic_home_stroke,
        title = "Home"
    ),
    JOURNEYS(
        route = "journeys",
        iconRes = R.drawable.ic_route_stroke,
        title = "Journeys"
    ),
    COMPANIONS(
        route = "companions",
        iconRes = R.drawable.ic_companions_stroke,
        title = "Companions"
    ),
    PROFILE(
        route = "profile",
        iconRes = R.drawable.ic_profile_icon,
        title = "Profile"
    )
}