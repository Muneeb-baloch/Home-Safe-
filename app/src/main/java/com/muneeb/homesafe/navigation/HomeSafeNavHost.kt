package com.muneeb.homesafe.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.muneeb.homesafe.feature.alerts.AlertsScreen
import com.muneeb.homesafe.feature.companions.AddTrustedContactScreen
import com.muneeb.homesafe.feature.companions.CompanionsScreen
import com.muneeb.homesafe.feature.companions.ContactDetailsScreen
import com.muneeb.homesafe.feature.home.HomeScreen
import com.muneeb.homesafe.feature.join.EnterOutingCodeScreen
import com.muneeb.homesafe.feature.join.JoinConfirmationScreen
import com.muneeb.homesafe.feature.join.ScanQrScreen
import com.muneeb.homesafe.feature.journeys.JourneyDetailsScreen
import com.muneeb.homesafe.feature.journeys.JourneysScreen
import com.muneeb.homesafe.feature.outing.ActiveOutingScreen
import com.muneeb.homesafe.feature.outing.ArrivalConfirmationScreen
import com.muneeb.homesafe.feature.outing.InviteCompanionsScreen
import com.muneeb.homesafe.feature.outing.JoinedOutingScreen
import com.muneeb.homesafe.feature.outing.StartOutingScreen
import com.muneeb.homesafe.feature.profile.EditProfileScreen
import com.muneeb.homesafe.feature.profile.ProfileScreen
import com.muneeb.homesafe.feature.profile.ProfileUiState

const val ALERTS_ROUTE = "alerts"
const val START_OUTING_ROUTE = "start_outing"
const val INVITE_COMPANIONS_ROUTE = "invite_companions"
const val ACTIVE_OUTING_ROUTE = "active_outing"
const val SCAN_QR_ROUTE = "scan_qr"
const val ENTER_OUTING_CODE_ROUTE = "enter_outing_code"
const val JOIN_CONFIRMATION_ROUTE = "join_confirmation"
const val JOINED_OUTING_ROUTE = "joined_outing"
const val ARRIVAL_CONFIRMATION_ROUTE = "arrival_confirmation"
const val JOURNEY_DETAILS_ROUTE = "journey_details"
const val CONTACT_DETAILS_ROUTE = "contact_details"
const val ADD_TRUSTED_CONTACT_ROUTE = "add_trusted_contact"
const val EDIT_PROFILE_ROUTE = "edit_profile"
private const val JOURNEY_ID_ARG = "journeyId"
private const val CONTACT_ID_ARG = "contactId"

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
                onStartOutingClick = { navController.navigate(START_OUTING_ROUTE) },
                onScanQrClick = { navController.navigate(SCAN_QR_ROUTE) },
                onEnterCodeClick = { navController.navigate(ENTER_OUTING_CODE_ROUTE) },
                onNotificationsClick = { navController.navigate(ALERTS_ROUTE) },
                onProfileClick = { navController.navigate(TopLevelDestination.PROFILE.route) },
                onViewAllJourneysClick = { navController.navigate(TopLevelDestination.JOURNEYS.route) }
            )
        }
        composable(TopLevelDestination.JOURNEYS.route) {
            JourneysScreen(
                onProfileClick = { navController.navigate(TopLevelDestination.PROFILE.route) },
                onJourneyClick = { journey ->
                    navController.navigate("$JOURNEY_DETAILS_ROUTE/${journey.id}")
                }
            )
        }
        composable(
            route = "$JOURNEY_DETAILS_ROUTE/{$JOURNEY_ID_ARG}",
            arguments = listOf(navArgument(JOURNEY_ID_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            JourneyDetailsScreen(
                journeyId = backStackEntry.arguments?.getString(JOURNEY_ID_ARG),
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(TopLevelDestination.COMPANIONS.route) {
            CompanionsScreen(
                onAddTrustedContactClick = { navController.navigate(ADD_TRUSTED_CONTACT_ROUTE) },
                onCompanionClick = { contactId ->
                    navController.navigate("$CONTACT_DETAILS_ROUTE/$contactId")
                }
            )
        }
        composable(
            route = "$CONTACT_DETAILS_ROUTE/{$CONTACT_ID_ARG}",
            arguments = listOf(navArgument(CONTACT_ID_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            ContactDetailsScreen(
                contactId = backStackEntry.arguments?.getString(CONTACT_ID_ARG),
                onBackClick = { navController.popBackStack() },
                onSharedOutingClick = { journeyId ->
                    navController.navigate("$JOURNEY_DETAILS_ROUTE/$journeyId")
                }
            )
        }
        composable(ADD_TRUSTED_CONTACT_ROUTE) {
            AddTrustedContactScreen(
                onBackClick = { navController.popBackStack() },
                onSendInviteClick = { navController.popBackStack() }
            )
        }
        composable(TopLevelDestination.PROFILE.route) {
            ProfileScreen(
                onEditProfileClick = { navController.navigate(EDIT_PROFILE_ROUTE) },
                onTrustedContactsClick = { navController.navigate(TopLevelDestination.COMPANIONS.route) }
            )
        }
        composable(EDIT_PROFILE_ROUTE) {
            EditProfileScreen(
                uiState = ProfileUiState(),
                onBackClick = { navController.popBackStack() },
                onSaveClick = { _, _, _ -> navController.popBackStack() }
            )
        }
        composable(ALERTS_ROUTE) {
            AlertsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(START_OUTING_ROUTE) {
            StartOutingScreen(
                onBackClick = { navController.popBackStack() },
                onAddTrustedContactClick = { navController.navigate(ADD_TRUSTED_CONTACT_ROUTE) },
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
                onHeadingHomeClick = { navController.navigate(ARRIVAL_CONFIRMATION_ROUTE) },
                onShareClick = {},
                onEndOutingClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            )
        }
        composable(SCAN_QR_ROUTE) {
            ScanQrScreen(
                onCloseClick = { navController.popBackStack() },
                onEnterCodeClick = { navController.navigate(ENTER_OUTING_CODE_ROUTE) }
            )
        }
        composable(ENTER_OUTING_CODE_ROUTE) {
            EnterOutingCodeScreen(
                onBackClick = { navController.popBackStack() },
                onFindOutingClick = { navController.navigate(JOIN_CONFIRMATION_ROUTE) },
                onScanQrClick = { navController.navigate(SCAN_QR_ROUTE) }
            )
        }
        composable(JOIN_CONFIRMATION_ROUTE) {
            JoinConfirmationScreen(
                onCloseClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                },
                onChangeHomeZoneClick = {},
                onJoinOutingClick = {
                    navController.navigate(JOINED_OUTING_ROUTE) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                },
                onDeclineClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            )
        }
        composable(JOINED_OUTING_ROUTE) {
            JoinedOutingScreen(
                onBackClick = { navController.popBackStack() },
                onMoreOptionsClick = {},
                onHeadingHomeClick = { navController.navigate(ARRIVAL_CONFIRMATION_ROUTE) },
                onLeaveOutingConfirmed = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            )
        }
        composable(ARRIVAL_CONFIRMATION_ROUTE) {
            ArrivalConfirmationScreen(
                onCloseClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                },
                onDoneClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                },
                onViewJourneyClick = {
                    navController.navigate("$JOURNEY_DETAILS_ROUTE/sample")
                }
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