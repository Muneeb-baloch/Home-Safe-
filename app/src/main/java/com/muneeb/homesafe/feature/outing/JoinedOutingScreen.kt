package com.muneeb.homesafe.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Amber
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.DestructiveRed
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HeroSub
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.PillDotGreen
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.domain.model.JourneyStatus
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.ConfirmActionSheet
import com.muneeb.homesafe.ui.components.HomeSafeOutlineButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.JourneyStep
import com.muneeb.homesafe.ui.components.JourneyStepState
import com.muneeb.homesafe.ui.components.JourneyStepper
import com.muneeb.homesafe.ui.components.Pill

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinedOutingScreen(
    onBackClick: () -> Unit,
    onMoreOptionsClick: () -> Unit,
    onHeadingHomeClick: () -> Unit,
    onLeaveOutingConfirmed: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: JoinedOutingUiState = JoinedOutingUiState()
) {
    var showLeaveSheet by remember { mutableStateOf(false) }

    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                title = stringResource(R.string.outing_active_title),
                trailing = {
                    CircleIconButton(
                        iconRes = R.drawable.ic_more_vertical,
                        contentDescription = stringResource(R.string.cd_more_options),
                        onClick = onMoreOptionsClick
                    )
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeSafePrimaryButton(
                    text = stringResource(R.string.outing_heading_home_button),
                    onClick = onHeadingHomeClick,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_walking),
                            contentDescription = null,
                            modifier = Modifier.width(18.dp)
                        )
                    }
                )

                HomeSafeOutlineButton(
                    text = stringResource(R.string.joined_outing_leave_button),
                    onClick = { showLeaveSheet = true },
                    contentColor = DestructiveRed,
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_log_out),
                            contentDescription = null,
                            tint = DestructiveRed,
                            modifier = Modifier.width(16.dp)
                        )
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null,
                        tint = Faint,
                        modifier = Modifier.width(13.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.outing_privacy_footer),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Normal,
                        color = Faint
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            JoinedOutingHero()

            JourneyStepper(steps = joinedJourneyStepsFor(uiState.journeyStatus))

            Text(
                text = stringResource(R.string.outing_companions_count_format, uiState.companions.size),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )

            if (uiState.companions.isEmpty()) {
                Text(
                    text = stringResource(R.string.joined_outing_no_companions_yet),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Subtle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(PureWhite)
                        .padding(16.dp)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(PureWhite)
                        .padding(horizontal = 16.dp)
                ) {
                    uiState.companions.forEachIndexed { index, companion ->
                        CompanionStatusRow(companion = companion)
                        if (index != uiState.companions.lastIndex) {
                            HorizontalDivider(color = Divider, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }

    if (showLeaveSheet) {
        ConfirmActionSheet(
            iconRes = R.drawable.ic_log_out,
            title = stringResource(R.string.leave_outing_sheet_title),
            body = stringResource(R.string.leave_outing_sheet_body),
            confirmText = stringResource(R.string.joined_outing_leave_button),
            dismissText = stringResource(R.string.leave_outing_sheet_stay_button),
            onConfirm = {
                showLeaveSheet = false
                onLeaveOutingConfirmed()
            },
            onDismiss = { showLeaveSheet = false }
        )
    }
}

@Composable
private fun joinedJourneyStepsFor(status: JourneyStatus): List<JourneyStep> {
    val arrived = status == JourneyStatus.ARRIVED || status == JourneyStatus.COMPLETED
    return listOf(
        JourneyStep(stringResource(R.string.joined_outing_step_joined), JourneyStepState.DONE, R.drawable.ic_check),
        JourneyStep(
            stringResource(R.string.outing_step_out),
            if (arrived) JourneyStepState.DONE else JourneyStepState.CURRENT,
            R.drawable.ic_walking
        ),
        JourneyStep(
            stringResource(R.string.outing_step_home),
            if (arrived) JourneyStepState.DONE else JourneyStepState.PENDING,
            R.drawable.ic_home_stroke
        )
    )
}

@Composable
private fun CompanionStatusRow(
    companion: JoinedCompanionUi,
    modifier: Modifier = Modifier
) {
    val displayName = companion.name.ifBlank { stringResource(R.string.generic_companion_name) }
    val subtitle = when (companion.subtitleKind) {
        CompanionSubtitleKind.STARTED -> stringResource(R.string.joined_outing_companion_started)
        CompanionSubtitleKind.JOINED -> stringResource(R.string.joined_outing_companion_joined_format, companion.subtitleTime)
        CompanionSubtitleKind.ARRIVED -> stringResource(R.string.joined_outing_companion_arrived_format, companion.subtitleTime)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Avatar(initial = displayName.take(1).uppercase())
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = displayName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Subtle
            )
        }
        if (companion.arrived) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.outing_step_home),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = DeepTeal
                )
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(Amber)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.joined_outing_status_out),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Amber
                )
            }
        }
    }
}

@Composable
private fun JoinedOutingHero(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(DeepTeal)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .size(160.dp)
                .border(1.5.dp, PureWhite.copy(alpha = 0.25f), CircleShape)
        )

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Pill(
                text = stringResource(R.string.joined_outing_pill_sample),
                background = PureWhite.copy(alpha = 0.16f),
                contentColor = PureWhite,
                dotColor = PillDotGreen
            )
            Text(
                text = stringResource(R.string.outing_youre_out_title),
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                color = PureWhite
            )
            Text(
                text = stringResource(R.string.joined_outing_youre_out_subtitle),
                fontSize = 13.sp,
                lineHeight = 19.sp,
                fontWeight = FontWeight.Normal,
                color = HeroSub
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JoinedOutingScreenPreview() {
    HomeSafeTheme {
        JoinedOutingScreen(
            onBackClick = {},
            onMoreOptionsClick = {},
            onHeadingHomeClick = {},
            onLeaveOutingConfirmed = {},
            uiState = JoinedOutingUiState(
                journeyStatus = JourneyStatus.OUT,
                companions = listOf(
                    JoinedCompanionUi("1", "Muneeb", CompanionSubtitleKind.STARTED, "", arrived = false),
                    JoinedCompanionUi("2", "Ahmed", CompanionSubtitleKind.JOINED, "8:05 PM", arrived = false),
                    JoinedCompanionUi("3", "Ali", CompanionSubtitleKind.ARRIVED, "11:02 PM", arrived = true)
                )
            )
        )
    }
}
