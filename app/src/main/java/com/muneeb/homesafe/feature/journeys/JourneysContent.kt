package com.muneeb.homesafe.feature.journeys

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.annotation.DrawableRes
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
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
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun JourneysContent(
    uiState: JourneysUiState,
    onFilterSelect: (JourneyFilter) -> Unit,
    onSearchClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onJourneyClick: (JourneyItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val filteredSections = remember(uiState.sections, uiState.selectedFilter) {
        uiState.sections.mapNotNull { section ->
            val journeys = section.journeys.filter { journey ->
                when (uiState.selectedFilter) {
                    JourneyFilter.ALL -> true
                    JourneyFilter.WITH_COMPANIONS -> journey.companionsCount > 0
                    JourneyFilter.SOLO -> journey.companionsCount == 0
                }
            }
            if (journeys.isEmpty()) null else section.copy(journeys = journeys)
        }
    }

    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            JourneysTopBar(
                onSearchClick = onSearchClick,
                onProfileClick = onProfileClick
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "subtitle") {
                Text(
                    text = stringResource(R.string.journeys_subtitle),
                    fontSize = 14.sp,
                    color = Muted,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item(key = "filters") {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(JourneyFilter.entries.toList(), key = { it.name }) { filter ->
                        FilterPill(
                            label = filterLabel(filter, uiState.totalJourneysCount),
                            selected = filter == uiState.selectedFilter,
                            onClick = { onFilterSelect(filter) }
                        )
                    }
                }
            }

            item(key = "hero") {
                SafeArrivalsBanner(
                    count = uiState.safeArrivalsCount,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            if (filteredSections.isEmpty()) {
                item(key = "empty") {
                    JourneysEmptyState(modifier = Modifier.padding(horizontal = 20.dp))
                }
            } else {
                filteredSections.forEach { section ->
                    item(key = "section_${section.label}") {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SectionHeaderRow(
                                label = section.label,
                                count = section.journeys.size
                            )

                            SectionCard(padding = 0.dp) {
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    section.journeys.forEachIndexed { index, journey ->
                                        JourneyRow(
                                            journey = journey,
                                            onClick = { onJourneyClick(journey) },
                                            modifier = Modifier.padding(vertical = 12.dp)
                                        )
                                        if (index != section.journeys.lastIndex) {
                                            HorizontalDivider(color = Divider, thickness = 1.dp)
                                        }
                                    }
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
                        text = stringResource(R.string.journeys_encrypted_footer),
                        fontSize = 11.sp,
                        color = Faint
                    )
                }
            }
        }
    }
}

@Composable
private fun JourneysTopBar(
    onSearchClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.journeys_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Ink,
            modifier = Modifier.weight(1f)
        )

        CircleIconButton(
            iconRes = R.drawable.ic_search,
            contentDescription = stringResource(R.string.cd_search),
            onClick = onSearchClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onProfileClick)
        ) {
            Avatar(initial = "M", size = 38.dp)
        }
    }
}

@Composable
private fun FilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = if (selected) PureWhite else Ink,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .then(
                if (selected) {
                    Modifier.background(DeepTeal)
                } else {
                    Modifier
                        .background(PureWhite)
                        .border(1.dp, Border, RoundedCornerShape(50))
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    )
}

@Composable
private fun SafeArrivalsBanner(
    count: Int,
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
                painter = painterResource(id = R.drawable.ic_shield_check),
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(22.dp)
            )
        }

        Column {
            Text(
                text = stringResource(R.string.journeys_safe_arrivals_format, count),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = PureWhite
            )
            Text(
                text = stringResource(R.string.journeys_safe_arrivals_subtitle),
                fontSize = 13.sp,
                color = HeroSub
            )
        }
    }
}

@Composable
private fun SectionHeaderRow(
    label: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Subtle,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(R.string.journeys_outings_count_format, count),
            fontSize = 13.sp,
            color = Subtle
        )
    }
}

@Composable
private fun JourneyRow(
    journey: JourneyItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val companionsLabel = if (journey.companionsCount == 0) {
        stringResource(R.string.journeys_solo_label)
    } else {
        stringResource(R.string.journeys_companions_count_format, journey.companionsCount)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IconTile(iconRes = journey.icon.toIconRes(), size = 34.dp, iconSize = 16.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = journey.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${journey.dateLabel} · ${journey.timeLabel} · $companionsLabel",
                fontSize = 12.sp,
                color = Subtle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(BrandTint)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = stringResource(R.string.cd_journey_status_icon),
                tint = DeepTeal,
                modifier = Modifier.size(10.dp)
            )
            Text(
                text = journey.statusLabel,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = DeepTeal
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = null,
            tint = Faint,
            modifier = Modifier
                .size(14.dp)
                .scale(scaleX = -1f, scaleY = 1f)
        )
    }
}

@Composable
private fun JourneysEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IconTile(
            iconRes = R.drawable.ic_route_stroke,
            size = 52.dp,
            iconSize = 24.dp
        )
        Text(
            text = stringResource(R.string.journeys_empty_title),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Ink,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.journeys_empty_subtitle),
            fontSize = 13.sp,
            color = Subtle,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun filterLabel(filter: JourneyFilter, totalCount: Int): String = when (filter) {
    JourneyFilter.ALL -> stringResource(R.string.journeys_filter_all_format, totalCount)
    JourneyFilter.WITH_COMPANIONS -> stringResource(R.string.journeys_filter_with_companions)
    JourneyFilter.SOLO -> stringResource(R.string.journeys_filter_solo)
}

@DrawableRes
private fun JourneyIcon.toIconRes(): Int = when (this) {
    JourneyIcon.NIGHT_OUT -> R.drawable.ic_moon
    JourneyIcon.DINING -> R.drawable.ic_utensils
    JourneyIcon.MOVIE -> R.drawable.ic_clapperboard
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JourneysContentPreview() {
    HomeSafeTheme {
        JourneysContent(
            uiState = JourneysUiState(),
            onFilterSelect = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Empty")
@Composable
private fun JourneysContentEmptyPreview() {
    HomeSafeTheme {
        JourneysContent(
            uiState = JourneysUiState(selectedFilter = JourneyFilter.SOLO, sections = emptyList()),
            onFilterSelect = {}
        )
    }
}
