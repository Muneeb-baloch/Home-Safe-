package com.muneeb.homesafe.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muneeb.homesafe.feature.home.HomeScreen
import com.muneeb.homesafe.feature.outing.ActiveOutingScreen
import com.muneeb.homesafe.feature.outing.InviteCompanionsScreen
import com.muneeb.homesafe.feature.outing.StartOutingScreen

const val ALERTS_ROUTE = "alerts"
const val START_OUTING_ROUTE = "start_outing"
const val INVITE_COMPANIONS_ROUTE = "invite_companions"
const val ACTIVE_OUTING_ROUTE = "active_outing"

@Composable
fun HomeSafeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.HOME.route,
        modifier = modifier
    ) {
        composable(TopLevelDestination.HOME.route) {
            HomeScreen(
                onStartOutingClick = { navController.navigate(START_OUTING_ROUTE) }
            )
        }
        composable(TopLevelDestination.JOURNEYS.route) {
            ScreenPlaceholder("Journeys Screen")
        }
        composable(TopLevelDestination.COMPANIONS.route) {
            ScreenPlaceholder("Companions Screen")
        }
        composable(TopLevelDestination.PROFILE.route) {
            ScreenPlaceholder("Profile Screen")
        }
        composable(ALERTS_ROUTE) {
            ScreenPlaceholder("Alerts Screen")
        }
        composable(START_OUTING_ROUTE) {
            StartOutingScreen(
                onBackClick = { navController.popBackStack() },
                onAddTrustedContactClick = { navController.navigate(INVITE_COMPANIONS_ROUTE) },
                onStartOutingClick = {
                    navController.navigate(ACTIVE_OUTING_ROUTE) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            )
        }
        composable(INVITE_COMPANIONS_ROUTE) {
            InviteCompanionsScreen(
                onBackClick = { navController.popBackStack() },
                onAddCompanionsClick = { navController.popBackStack() }
            )
        }
        composable(ACTIVE_OUTING_ROUTE) {
            ActiveOutingScreen(
                onBackClick = { navController.popBackStack() },
                onManageCompanionsClick = {},
                onMoreOptionsClick = {},
                onHeadingHomeClick = {},
                onShareClick = {},
                onEndOutingClick = {}
            )
        }
    }
}

@Composable
private fun ScreenPlaceholder(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title)
    }
}