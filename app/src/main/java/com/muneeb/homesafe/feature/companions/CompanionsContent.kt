package com.muneeb.homesafe.feature.companions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.DashedActionButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun CompanionsContent(
    uiState: CompanionsUiState,
    onFilterSelect: (CompanionsFilter) -> Unit,
    onSearchClick: () -> Unit = {},
    onAddTrustedContactClick: () -> Unit = {},
    onCompanionClick: (String) -> Unit = {},
    onTrustClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val listedCompanions = when (uiState.selectedFilter) {
        CompanionsFilter.TRUSTED -> uiState.trustedCompanions
        CompanionsFilter.RECENT -> uiState.recentCompanions
    }

    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            CompanionsTopBar(
                onSearchClick = onSearchClick,
                onAddTrustedContactClick = onAddTrustedContactClick
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
                    text = stringResource(R.string.companions_subtitle),
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
                    items(CompanionsFilter.entries.toList(), key = { it.name }) { filter ->
                        FilterPill(
                            label = when (filter) {
                                CompanionsFilter.TRUSTED -> stringResource(
                                    R.string.companions_filter_trusted_format,
                                    uiState.trustedCompanions.size
                                )
                                CompanionsFilter.RECENT -> stringResource(R.string.companions_filter_recent)
                            },
                            selected = filter == uiState.selectedFilter,
                            onClick = { onFilterSelect(filter) }
                        )
                    }
                }
            }

            if (listedCompanions.isEmpty()) {
                item(key = "empty") {
                    CompanionsEmptyState(
                        isTrustedFilter = uiState.selectedFilter == CompanionsFilter.TRUSTED,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            } else {
                item(key = "list") {
                    SectionCard(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        padding = 0.dp
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            listedCompanions.forEachIndexed { index, companion ->
                                CompanionRow(
                                    companion = companion,
                                    onClick = { onCompanionClick(companion.id) },
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                                if (index != listedCompanions.lastIndex) {
                                    HorizontalDivider(color = Divider, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }
            }

            if (uiState.recentSuggestions.isNotEmpty()) {
                item(key = "recent_label") {
                    Text(
                        text = stringResource(R.string.companions_recent_section_label),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Subtle,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }

                item(key = "recent_suggestions") {
                    SectionCard(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        padding = 0.dp
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            uiState.recentSuggestions.forEachIndexed { index, suggestion ->
                                RecentSuggestionRow(
                                    suggestion = suggestion,
                                    onTrustClick = { onTrustClick(suggestion.id) },
                                    modifier = Modifier.padding(vertical = 12.dp)
                                )
                                if (index != uiState.recentSuggestions.lastIndex) {
                                    HorizontalDivider(color = Divider, thickness = 1.dp)
                                }
                            }
                        }
                    }
                }
            }

            item(key = "add_trusted_contact") {
                DashedActionButton(
                    text = stringResource(R.string.cd_add_trusted_contact),
                    onClick = onAddTrustedContactClick,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
private fun CompanionsTopBar(
    onSearchClick: () -> Unit,
    onAddTrustedContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.companions_title),
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
                .size(40.dp)
                .clip(CircleShape)
                .background(DeepTeal)
                .clickable(onClick = onAddTrustedContactClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_plus),
                contentDescription = stringResource(R.string.cd_add_trusted_contact),
                tint = PureWhite,
                modifier = Modifier.size(19.dp)
            )
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
private fun CompanionRow(
    companion: CompanionSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Avatar(
            initial = companion.initial,
            background = if (companion.avatarStyle == AvatarStyle.SOLID) DeepTeal else BrandTint,
            contentColor = if (companion.avatarStyle == AvatarStyle.SOLID) PureWhite else DeepTeal
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = companion.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Text(
                text = pluralStringResource(
                    R.plurals.companions_outings_count,
                    companion.outingsCount,
                    companion.outingsCount
                ) + " · " + stringResource(R.string.companions_last_outing_format, companion.lastOutingLabel),
                fontSize = 12.sp,
                color = Subtle
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = null,
            tint = DeepTeal,
            modifier = Modifier.size(18.dp)
        )

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
private fun RecentSuggestionRow(
    suggestion: RecentCompanionSuggestion,
    onTrustClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Avatar(
            initial = suggestion.initial,
            background = BrandTint,
            contentColor = DeepTeal
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = suggestion.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Text(
                text = stringResource(R.string.companions_joined_format, suggestion.joinedOutingLabel),
                fontSize = 12.sp,
                color = Subtle
            )
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(PureWhite)
                .border(1.dp, DeepTeal, RoundedCornerShape(50))
                .clickable(onClick = onTrustClick)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null,
                tint = DeepTeal,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = stringResource(R.string.companions_trust_button),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = DeepTeal
            )
        }
    }
}

@Composable
private fun CompanionsEmptyState(
    isTrustedFilter: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IconTile(
            iconRes = R.drawable.ic_companions_stroke,
            size = 52.dp,
            iconSize = 24.dp
        )
        Text(
            text = stringResource(
                if (isTrustedFilter) R.string.companions_empty_trusted_title else R.string.companions_empty_recent_title
            ),
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Ink,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(
                if (isTrustedFilter) R.string.companions_empty_trusted_subtitle else R.string.companions_empty_recent_subtitle
            ),
            fontSize = 13.sp,
            color = Subtle,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CompanionsContentPreview() {
    HomeSafeTheme {
        CompanionsContent(
            uiState = CompanionsUiState(),
            onFilterSelect = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Empty")
@Composable
private fun CompanionsContentEmptyPreview() {
    HomeSafeTheme {
        CompanionsContent(
            uiState = CompanionsUiState(trustedCompanions = emptyList(), recentSuggestions = emptyList()),
            onFilterSelect = {}
        )
    }
}
