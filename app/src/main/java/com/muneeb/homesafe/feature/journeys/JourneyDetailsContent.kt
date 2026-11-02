package com.muneeb.homesafe.feature.journeys

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HeroSub
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.ContactRow
import com.muneeb.homesafe.ui.components.FeatureRow
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun JourneyDetailsContent(
    uiState: JourneyDetailsUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                title = stringResource(R.string.journey_details_title)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "heading") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = uiState.title,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = uiState.dateTimeLabel,
                            fontSize = 14.sp,
                            color = Muted
                        )
                        StatusPill(text = stringResource(R.string.journey_details_status_arrived_safely))
                    }
                }
            }

            item(key = "hero") {
                ArrivalHeroBanner(
                    timeLabel = uiState.arrivalTimeLabel,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item(key = "timeline_label") {
                Text(
                    text = stringResource(R.string.journey_details_timeline_label),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Subtle,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item(key = "timeline") {
                JourneyTimelineCard(
                    steps = uiState.timeline,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            if (uiState.companions.isEmpty()) {
                item(key = "solo") {
                    SoloJourneyCard(modifier = Modifier.padding(horizontal = 20.dp))
                }
            } else {
                item(key = "companions_label") {
                    Text(
                        text = stringResource(
                            R.string.journey_details_companions_notified_format,
                            uiState.companions.size
                        ),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Subtle,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                item(key = "companions") {
                    SectionCard(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        padding = 0.dp
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            uiState.companions.forEachIndexed { index, companion ->
                                ContactRow(
                                    initial = companion.initial,
                                    name = companion.name,
                                    subtitle = companion.email,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                    trailing = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_check),
                                                contentDescription = null,
                                                tint = DeepTeal,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = stringResource(R.string.journey_details_notified_label),
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = DeepTeal
                                            )
                                        }
                                    }
                                )
                                if (index != uiState.companions.lastIndex) {
                                    HorizontalDivider(color = Divider, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }
            }

            item(key = "footer") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock),
                        contentDescription = null,
                        tint = Faint,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(R.string.journey_details_footer),
                        fontSize = 11.sp,
                        color = Faint
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusPill(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(BrandTint)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = null,
            tint = DeepTeal,
            modifier = Modifier.size(11.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = DeepTeal
        )
    }
}

@Composable
private fun ArrivalHeroBanner(
    timeLabel: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DeepTeal)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(PureWhite.copy(alpha = 0.16f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_stroke),
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(20.dp)
            )
        }

        Column {
            Text(
                text = stringResource(R.string.journey_details_hero_title),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = PureWhite
            )
            Text(
                text = stringResource(R.string.journey_details_hero_time_format, timeLabel),
                fontSize = 13.sp,
                color = HeroSub
            )
        }
    }
}

@Composable
private fun JourneyTimelineCard(
    steps: List<JourneyTimelineStep>,
    modifier: Modifier = Modifier
) {
    SectionCard(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth()) {
            steps.forEachIndexed { index, step ->
                JourneyTimelineRow(
                    step = step,
                    showLine = index != steps.lastIndex
                )
            }
        }
    }
}

@Composable
private fun JourneyTimelineRow(
    step: JourneyTimelineStep,
    showLine: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .then(
                        if (step.isFinal) {
                            Modifier.background(DeepTeal)
                        } else {
                            Modifier
                                .background(PureWhite)
                                .border(2.dp, DeepTeal, CircleShape)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (step.isFinal) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                        tint = PureWhite,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }
            if (showLine) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(DeepTeal)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = if (showLine) 20.dp else 0.dp)
        ) {
            Text(
                text = step.label(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = step.time,
                fontSize = 12.sp,
                color = Subtle
            )
        }
    }
}

@Composable
private fun JourneyTimelineStep.label(): String = when (type) {
    JourneyStepType.OUTING_STARTED -> stringResource(R.string.journey_details_step_outing_started)
    JourneyStepType.LEFT_HOME -> stringResource(R.string.journey_details_step_left_home)
    JourneyStepType.HEADING_HOME -> stringResource(R.string.journey_details_step_heading_home)
    JourneyStepType.ARRIVED_HOME -> stringResource(R.string.journey_details_step_arrived_home)
}

@Composable
private fun SoloJourneyCard(modifier: Modifier = Modifier) {
    SectionCard(modifier = modifier) {
        FeatureRow(
            iconRes = R.drawable.ic_user,
            title = stringResource(R.string.journey_details_solo_title),
            subtitle = stringResource(R.string.journey_details_solo_subtitle)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "With companions")
@Composable
private fun JourneyDetailsContentPreview() {
    HomeSafeTheme {
        JourneyDetailsContent(
            uiState = JourneyDetailsSampleData.forId("1"),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Solo")
@Composable
private fun JourneyDetailsContentSoloPreview() {
    HomeSafeTheme {
        JourneyDetailsContent(
            uiState = JourneyDetailsSampleData.forId("4"),
            onBackClick = {}
        )
    }
}
