package com.muneeb.homesafe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muneeb.homesafe.feature.setup.SetupBackgroundLocationScreen
import com.muneeb.homesafe.feature.setup.SetupHomeLocationScreen
import com.muneeb.homesafe.feature.setup.SetupLocationAccessScreen
import com.muneeb.homesafe.feature.setup.SetupNotificationsScreen

const val SETUP_LOCATION_ROUTE = "setup_location"
const val SETUP_BACKGROUND_LOCATION_ROUTE = "setup_background_location"
const val SETUP_NOTIFICATIONS_ROUTE = "setup_notifications"
const val SETUP_HOME_LOCATION_ROUTE = "setup_home_location"
const val MAIN_ROUTE = "main"

@Composable
fun HomeSafeApp(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SETUP_LOCATION_ROUTE,
        modifier = modifier
    ) {
        composable(SETUP_LOCATION_ROUTE) {
            SetupLocationAccessScreen(
                onBackClick = { navController.popBackStack() },
                onAllowLocationClick = { navController.navigate(SETUP_BACKGROUND_LOCATION_ROUTE) }
            )
        }
        composable(SETUP_BACKGROUND_LOCATION_ROUTE) {
            SetupBackgroundLocationScreen(
                onBackClick = { navController.popBackStack() },
                onOpenSettingsClick = { navController.navigate(SETUP_NOTIFICATIONS_ROUTE) },
                onNotNowClick = { navController.navigate(SETUP_NOTIFICATIONS_ROUTE) }
            )
        }
        composable(SETUP_NOTIFICATIONS_ROUTE) {
            SetupNotificationsScreen(
                onBackClick = { navController.popBackStack() },
                onTurnOnNotificationsClick = { navController.navigate(SETUP_HOME_LOCATION_ROUTE) },
                onMaybeLaterClick = { navController.navigate(SETUP_HOME_LOCATION_ROUTE) }
            )
        }
        composable(SETUP_HOME_LOCATION_ROUTE) {
            SetupHomeLocationScreen(
                onBackClick = { navController.popBackStack() },
                onUseCurrentLocationClick = {},
                onEditAddressClick = {},
                onSaveHomeClick = {
                    navController.navigate(MAIN_ROUTE) {
                        popUpTo(SETUP_LOCATION_ROUTE) { inclusive = true }
                    }
                }
            )
        }
        composable(MAIN_ROUTE) {
            MainScreen()
        }
    }
}
