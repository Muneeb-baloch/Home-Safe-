package com.muneeb.homesafe.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.muneeb.homesafe.feature.join.EnterOutingCodeViewModel
import com.muneeb.homesafe.feature.join.JoinConfirmationScreen
import com.muneeb.homesafe.feature.join.JoinConfirmationViewModel
import com.muneeb.homesafe.feature.join.ScanQrScreen
import com.muneeb.homesafe.feature.journeys.JourneyDetailsScreen
import com.muneeb.homesafe.feature.journeys.JourneysScreen
import com.muneeb.homesafe.feature.outing.ActiveOutingScreen
import com.muneeb.homesafe.feature.outing.ActiveOutingViewModel
import com.muneeb.homesafe.feature.outing.ArrivalConfirmationScreen
import com.muneeb.homesafe.feature.outing.InviteCompanionsScreen
import com.muneeb.homesafe.feature.outing.JoinedOutingScreen
import com.muneeb.homesafe.feature.outing.JoinedOutingViewModel
import com.muneeb.homesafe.feature.outing.StartOutingScreen
import com.muneeb.homesafe.feature.outing.StartOutingViewModel
import com.muneeb.homesafe.feature.profile.EditProfileScreen
import com.muneeb.homesafe.feature.profile.ProfileScreen
import com.muneeb.homesafe.feature.profile.ProfileViewModel

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
private const val OUTING_ID_ARG = "outingId"

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
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            EditProfileScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onSaveClick = { name, _, _ ->
                    viewModel.saveProfile(name)
                    navController.popBackStack()
                }
            )
        }
        composable(ALERTS_ROUTE) {
            AlertsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(START_OUTING_ROUTE) {
            val viewModel: StartOutingViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(uiState.createdOutingId) {
                uiState.createdOutingId?.let { outingId ->
                    navController.navigate("$ACTIVE_OUTING_ROUTE/$outingId") {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            }
            StartOutingScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onAddTrustedContactClick = { navController.navigate(ADD_TRUSTED_CONTACT_ROUTE) },
                onStartOutingClick = { viewModel.startOuting() }
            )
        }
        composable(INVITE_COMPANIONS_ROUTE) {
            InviteCompanionsScreen(
                onBackClick = { navController.popBackStack() },
                onAddCompanionsClick = { navController.popBackStack() }
            )
        }
        composable(
            route = "$ACTIVE_OUTING_ROUTE/{$OUTING_ID_ARG}",
            arguments = listOf(navArgument(OUTING_ID_ARG) { type = NavType.StringType })
        ) {
            val viewModel: ActiveOutingViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            ActiveOutingScreen(
                uiState = uiState,
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
            val viewModel: EnterOutingCodeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(uiState.foundOutingId) {
                uiState.foundOutingId?.let { outingId ->
                    navController.navigate("$JOIN_CONFIRMATION_ROUTE/$outingId")
                }
            }
            EnterOutingCodeScreen(
                uiState = uiState,
                onCodeChange = viewModel::onCodeChange,
                onBackClick = { navController.popBackStack() },
                onFindOutingClick = { viewModel.findOuting() },
                onScanQrClick = { navController.navigate(SCAN_QR_ROUTE) }
            )
        }
        composable(
            route = "$JOIN_CONFIRMATION_ROUTE/{$OUTING_ID_ARG}",
            arguments = listOf(navArgument(OUTING_ID_ARG) { type = NavType.StringType })
        ) { backStackEntry ->
            val outingId = backStackEntry.arguments?.getString(OUTING_ID_ARG).orEmpty()
            val viewModel: JoinConfirmationViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            LaunchedEffect(uiState.joined) {
                if (uiState.joined) {
                    navController.navigate("$JOINED_OUTING_ROUTE/$outingId") {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            }
            JoinConfirmationScreen(
                uiState = uiState,
                onCloseClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                },
                onChangeHomeZoneClick = {},
                onJoinOutingClick = { viewModel.joinOuting() },
                onDeclineClick = {
                    navController.navigate(TopLevelDestination.HOME.route) {
                        popUpTo(TopLevelDestination.HOME.route) { inclusive = false }
                    }
                }
            )
        }
        composable(
            route = "$JOINED_OUTING_ROUTE/{$OUTING_ID_ARG}",
            arguments = listOf(navArgument(OUTING_ID_ARG) { type = NavType.StringType })
        ) {
            val viewModel: JoinedOutingViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            JoinedOutingScreen(
                uiState = uiState,
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