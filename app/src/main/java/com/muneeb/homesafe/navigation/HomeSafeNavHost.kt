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
            HomeScreen()
        }
        composable(TopLevelDestination.JOURNEYS.route) {
            // Replace with actual JourneysScreen()
            ScreenPlaceholder("Journeys Screen")
        }
        composable(TopLevelDestination.ALERTS.route) {
            // Replace with actual AlertsScreen()
            ScreenPlaceholder("Alerts Screen")
        }
        composable(TopLevelDestination.PROFILE.route) {
            // Replace with actual ProfileScreen()
            ScreenPlaceholder("Profile Screen")
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