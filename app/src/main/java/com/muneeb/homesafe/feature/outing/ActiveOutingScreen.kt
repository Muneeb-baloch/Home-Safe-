package com.muneeb.homesafe.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.DestructiveRed
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
import com.muneeb.homesafe.ui.components.CodeBox
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
fun ActiveOutingScreen(
    onBackClick: () -> Unit,
    onManageCompanionsClick: () -> Unit,
    onMoreOptionsClick: () -> Unit,
    onHeadingHomeClick: () -> Unit,
    onShareClick: () -> Unit,
    onEndOutingClick: () -> Unit,
    modifier: Modifier = Modifier,
    uiState: ActiveOutingUiState = ActiveOutingUiState()
) {
    var showEndSheet by remember { mutableStateOf(false) }

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HomeSafeOutlineButton(
                        text = stringResource(R.string.outing_share_button),
                        onClick = onShareClick,
                        modifier = Modifier.weight(1f),
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null,
                                modifier = Modifier.width(16.dp)
                            )
                        }
                    )
                    HomeSafeOutlineButton(
                        text = stringResource(R.string.outing_end_outing_button),
                        onClick = { showEndSheet = true },
                        modifier = Modifier.weight(1f),
                        contentColor = DestructiveRed,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_flag),
                                contentDescription = null,
                                modifier = Modifier.width(16.dp)
                            )
                        }
                    )
                }

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
                        text = stringResource(R.string.outing_active_privacy_footer),
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
            ActiveOutingHero()

            JourneyStepper(steps = journeyStepsFor(uiState.journeyStatus))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.active_outing_code_label),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Ink
                )
                CodeBox(
                    code = uiState.outingCode,
                    onQrClick = {},
                    onShareClick = onShareClick,
                    qrContentDescription = stringResource(R.string.cd_qr_code),
                    shareContentDescription = stringResource(R.string.cd_share_code)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.outing_companions_count_format, uiState.companions.size),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Ink,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = stringResource(R.string.outing_manage_button),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = DeepTeal,
                    modifier = Modifier.clickable(onClick = onManageCompanionsClick)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (uiState.companions.isEmpty()) {
                    Text(
                        text = stringResource(R.string.outing_no_companions_yet),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Subtle,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    val companionNames = uiState.companions.map {
                        it.name.ifBlank { stringResource(R.string.generic_companion_name) }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        companionNames.take(3).forEach { name ->
                            Avatar(initial = name.take(1).uppercase(), size = 32.dp)
                        }
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = companionNames.joinToString(", "),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Subtle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.outing_notified_on_arrival),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Subtle
                    )
                }
            }
        }
    }

    if (showEndSheet) {
        ConfirmActionSheet(
            iconRes = R.drawable.ic_flag,
            title = stringResource(R.string.end_outing_sheet_title),
            body = stringResource(R.string.end_outing_sheet_body),
            confirmText = stringResource(R.string.outing_end_outing_button),
            dismissText = stringResource(R.string.end_outing_sheet_keep_active_button),
            onConfirm = {
                showEndSheet = false
                onEndOutingClick()
            },
            onDismiss = { showEndSheet = false }
        )
    }
}

@Composable
private fun journeyStepsFor(status: JourneyStatus): List<JourneyStep> {
    val arrived = status == JourneyStatus.ARRIVED || status == JourneyStatus.COMPLETED
    return listOf(
        JourneyStep(stringResource(R.string.outing_step_started), JourneyStepState.DONE, R.drawable.ic_check),
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
private fun ActiveOutingHero(modifier: Modifier = Modifier) {
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
                text = stringResource(R.string.outing_active_pill_sample),
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
                text = stringResource(R.string.outing_youre_out_subtitle),
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
private fun ActiveOutingScreenPreview() {
    HomeSafeTheme {
        ActiveOutingScreen(
            onBackClick = {},
            onManageCompanionsClick = {},
            onMoreOptionsClick = {},
            onHeadingHomeClick = {},
            onShareClick = {},
            onEndOutingClick = {},
            uiState = ActiveOutingUiState(
                outingCode = "7K4P",
                journeyStatus = JourneyStatus.OUT,
                companions = listOf(
                    OutingCompanionUi(uid = "1", name = "Ahmed"),
                    OutingCompanionUi(uid = "2", name = "Ali"),
                    OutingCompanionUi(uid = "3", name = "Hamza")
                )
            )
        )
    }
}
