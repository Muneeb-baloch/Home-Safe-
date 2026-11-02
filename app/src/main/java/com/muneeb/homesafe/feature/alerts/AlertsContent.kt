package com.muneeb.homesafe.feature.alerts

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun AlertsContent(
    uiState: AlertsUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                title = stringResource(R.string.alerts_title)
            )
        }
    ) {
        if (uiState.sections.isEmpty()) {
            AlertsEmptyState(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                uiState.sections.forEach { section ->
                    item(key = "label_${section.label}") {
                        Text(
                            text = section.label,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Subtle,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }

                    item(key = "section_${section.label}") {
                        SectionCard(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            padding = 0.dp
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                section.alerts.forEachIndexed { index, alert ->
                                    AlertRow(
                                        alert = alert,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                    if (index != section.alerts.lastIndex) {
                                        HorizontalDivider(color = Divider, thickness = 1.dp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertRow(
    alert: AlertItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconTile(
            iconRes = alert.type.toIconRes(),
            background = DeepTeal,
            tint = PureWhite
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = alert.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Text(
                text = alert.meta,
                fontSize = 12.sp,
                color = Subtle
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (alert.isUnread) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(DeepTeal)
                )
            }
            Text(
                text = alert.timeLabel,
                fontSize = 11.sp,
                color = Faint
            )
        }
    }
}

@Composable
private fun AlertsEmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconTile(
                iconRes = R.drawable.ic_bell_outline,
                size = 52.dp,
                iconSize = 24.dp
            )
            Text(
                text = stringResource(R.string.alerts_empty_title),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Ink,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.alerts_empty_subtitle),
                fontSize = 13.sp,
                color = Subtle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@DrawableRes
private fun AlertType.toIconRes(): Int = when (this) {
    AlertType.ARRIVAL -> R.drawable.ic_home_stroke
    AlertType.INVITE -> R.drawable.ic_user_plus
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AlertsContentPreview() {
    HomeSafeTheme {
        AlertsContent(
            uiState = AlertsUiState(),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Empty")
@Composable
private fun AlertsContentEmptyPreview() {
    HomeSafeTheme {
        AlertsContent(
            uiState = AlertsUiState(sections = emptyList()),
            onBackClick = {}
        )
    }
}
