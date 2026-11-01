package com.muneeb.homesafe.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HeroSub
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.MapBg
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.NotificationRed
import com.muneeb.homesafe.core.designsystem.theme.PillDotGreen
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import java.time.LocalTime

@Composable
fun HomeContent(
    uiState: HomeUiState,
    onStartJourneyClick: () -> Unit,
    onScanQrClick: () -> Unit = {},
    onEnterCodeClick: () -> Unit = {},
    onChangeHomeLocationClick: () -> Unit = {},
    onViewAllJourneysClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        background = PureWhite,
        topBar = {
            HomeTopBar(
                uiState = uiState,
                onNotificationsClick = onNotificationsClick,
                onProfileClick = onProfileClick
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "${greetingForTime()}, ${uiState.userName}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Muted
            )

            HomeHeroCard(uiState = uiState)

            Button(
                onClick = onStartJourneyClick,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 15.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepTeal,
                    contentColor = PureWhite
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.DirectionsWalk,
                    contentDescription = null,
                    modifier = Modifier.size(19.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Start an outing",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onScanQrClick,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = PureWhite,
                        contentColor = Ink
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.QrCodeScanner,
                        contentDescription = null,
                        tint = DeepTeal,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(text = "Scan QR", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

                OutlinedButton(
                    onClick = onEnterCodeClick,
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Border),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = PureWhite,
                        contentColor = Ink
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Dialpad,
                        contentDescription = null,
                        tint = DeepTeal,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(text = "Enter code", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                SectionHeader(
                    title = "Home location",
                    titleFontSize = 15.sp,
                    actionLabel = "Change",
                    onActionClick = onChangeHomeLocationClick
                )

                HomeLocationMap(geofenceLabel = uiState.geofenceLabel)

                Text(
                    text = "${uiState.homeLocationLabel} · monitored automatically",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Muted
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SectionHeader(
                    title = "Recent journeys",
                    titleFontSize = 16.sp,
                    actionLabel = "View all",
                    onActionClick = onViewAllJourneysClick
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    uiState.recentJourneys.forEachIndexed { index, journey ->
                        RecentJourneyRow(journey = journey)
                        if (index != uiState.recentJourneys.lastIndex) {
                            HorizontalDivider(color = Divider, thickness = 1.dp)
                        }
                    }
                }
            }

            Text(
                text = "HomeSafe shares arrival status, never your live location.",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = Faint,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun HomeTopBar(
    uiState: HomeUiState,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(PureWhite)
            .padding(start = 20.dp, end = 20.dp, top = 4.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(color = DeepTeal, shape = RoundedCornerShape(9.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_stroke),
                contentDescription = "HomeSafe Logo",
                tint = PureWhite,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "HomeSafe",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Ink
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clickable(onClick = onNotificationsClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell_outline),
                    contentDescription = "Notifications",
                    tint = Ink,
                    modifier = Modifier.size(20.dp)
                )
                if (uiState.hasUnreadNotifications) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = (-7).dp, y = 6.dp)
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(PureWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(NotificationRed)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(2.dp))

            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(BrandTint)
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.userName.take(1).uppercase(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = DeepTeal
                )
            }
        }
    }
}

@Composable
private fun HomeHeroCard(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    val badgeLabel = if (uiState.isHomeZoneActive) "Home zone active" else "Away from home"
    val title = if (uiState.hasActiveOuting) "Outing in progress" else "You're home"
    val subtitle = if (uiState.hasActiveOuting) {
        "Your location is being shared with trusted companions."
    } else {
        "No active outing. Your location is monitored privately, not shared."
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DeepTeal)
    ) {
        val cardWidth = maxWidth

        Box(
            modifier = Modifier
                .offset(x = cardWidth * (238f / 350f), y = (-34).dp)
                .size(150.dp)
                .border(1.5.dp, PureWhite.copy(alpha = 0.14f), CircleShape)
        )

        Canvas(
            modifier = Modifier
                .offset(x = cardWidth * (272f / 350f), y = 6.dp)
                .size(74.dp)
        ) {
            drawCircle(
                color = PureWhite.copy(alpha = 0.4f),
                radius = size.minDimension / 2f,
                style = Stroke(
                    width = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
                )
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_home_stroke),
            contentDescription = null,
            tint = PureWhite,
            modifier = Modifier
                .offset(x = cardWidth * (296f / 350f), y = 30.dp)
                .size(26.dp)
        )

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(RoundedCornerShape(50))
                    .background(PureWhite.copy(alpha = 0.16f))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(PillDotGreen)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = badgeLabel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = PureWhite
                )
            }

            Text(
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                color = PureWhite
            )

            Text(
                text = subtitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 18.sp,
                color = HeroSub,
                modifier = Modifier.widthIn(max = 270.dp)
            )
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    titleFontSize: TextUnit,
    actionLabel: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = titleFontSize,
            fontWeight = FontWeight.Medium,
            color = Ink,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = actionLabel,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = DeepTeal,
            modifier = Modifier.clickable(onClick = onActionClick)
        )
    }
}

@Composable
private fun HomeLocationMap(
    geofenceLabel: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MapBg)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val lineWidth = 1.dp.toPx()
            val gridColor = DeepTeal.copy(alpha = 0.07f)

            drawLine(gridColor, Offset(0f, 28.dp.toPx()), Offset(w, 28.dp.toPx()), lineWidth)
            drawLine(gridColor, Offset(0f, 60.dp.toPx()), Offset(w, 60.dp.toPx()), lineWidth)
            drawLine(gridColor, Offset(w * 0.305f, 0f), Offset(w * 0.305f, h), lineWidth)
            drawLine(gridColor, Offset(w * 0.695f, 0f), Offset(w * 0.695f, h), lineWidth)

            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))

            drawCircle(
                color = DeepTeal.copy(alpha = 0.45f),
                radius = 40.dp.toPx(),
                center = Offset(w / 2f, 4.dp.toPx() + 40.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx(), pathEffect = dashEffect)
            )
            drawCircle(
                color = DeepTeal.copy(alpha = 0.25f),
                radius = 28.dp.toPx(),
                center = Offset(w / 2f, 16.dp.toPx() + 28.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 29.dp)
                .size(30.dp)
                .clip(CircleShape)
                .background(DeepTeal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_stroke),
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(16.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(50))
                .background(PureWhite)
                .padding(horizontal = 9.dp, vertical = 3.dp)
        ) {
            Text(
                text = geofenceLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = DeepTeal
            )
        }
    }
}

@Composable
private fun RecentJourneyRow(
    journey: RecentJourney,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(BrandTint),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (journey.icon) {
                    JourneyIcon.NIGHT_OUT -> Icons.Filled.NightlightRound
                    JourneyIcon.DINING -> Icons.Filled.Restaurant
                },
                contentDescription = null,
                tint = DeepTeal,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = journey.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${journey.dateLabel} · ${journey.companionsLabel}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Subtle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = DeepTeal,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = journey.statusLabel,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = DeepTeal
            )
        }
    }
}

private fun greetingForTime(hour: Int = LocalTime.now().hour): String = when (hour) {
    in 5..11 -> "Good morning"
    in 12..16 -> "Good afternoon"
    in 17..20 -> "Good evening"
    else -> "Good night"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    HomeSafeTheme {
        HomeContent(
            uiState = HomeUiState(),
            onStartJourneyClick = {}
        )
    }
}
