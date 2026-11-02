package com.muneeb.homesafe.feature.companions

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.DestructiveRed
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.HomeSafeOutlineButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun ContactDetailsContent(
    uiState: ContactDetailsUiState,
    onBackClick: () -> Unit,
    onMoreOptionsClick: () -> Unit = {},
    onInviteToOutingClick: () -> Unit = {},
    onMessageClick: () -> Unit = {},
    onSharedOutingClick: (String) -> Unit = {},
    onRemoveFromTrustedClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                title = stringResource(R.string.contact_details_title),
                trailing = {
                    CircleIconButton(
                        iconRes = R.drawable.ic_more_vertical,
                        contentDescription = stringResource(R.string.cd_more_options),
                        onClick = onMoreOptionsClick
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "header") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Avatar(
                        initial = uiState.name.take(1),
                        size = 72.dp,
                        fontSize = 26.sp,
                        background = if (uiState.avatarStyle == AvatarStyle.SOLID) DeepTeal else BrandTint,
                        contentColor = if (uiState.avatarStyle == AvatarStyle.SOLID) PureWhite else DeepTeal
                    )
                    Text(
                        text = uiState.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                    if (uiState.isTrusted) {
                        TrustedPill()
                    }
                    Text(
                        text = uiState.phone,
                        fontSize = 14.sp,
                        color = Muted
                    )
                }
            }

            item(key = "stats") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        value = uiState.outingsCount.toString(),
                        label = stringResource(R.string.contact_details_outings_together_label),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = uiState.lastOutingLabel,
                        label = stringResource(R.string.contact_details_last_outing_label),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item(key = "actions") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HomeSafePrimaryButton(
                        text = stringResource(R.string.contact_details_invite_button),
                        onClick = onInviteToOutingClick,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_walking),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                    HomeSafeOutlineButton(
                        text = stringResource(R.string.contact_details_message_button),
                        onClick = onMessageClick,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_message),
                                contentDescription = null,
                                tint = Ink,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }

            item(key = "shared_outings_label") {
                Text(
                    text = stringResource(R.string.contact_details_shared_outings_label),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Subtle,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item(key = "shared_outings") {
                SectionCard(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    padding = 0.dp
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        uiState.sharedOutings.forEachIndexed { index, outing ->
                            SharedOutingRow(
                                outing = outing,
                                onClick = { onSharedOutingClick(outing.journeyId) },
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                            if (index != uiState.sharedOutings.lastIndex) {
                                HorizontalDivider(color = Divider, thickness = 1.dp)
                            }
                        }
                    }
                }
            }

            if (uiState.isTrusted) {
                item(key = "remove") {
                    Text(
                        text = stringResource(R.string.contact_details_remove_from_trusted),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = DestructiveRed,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .clickable(onClick = onRemoveFromTrustedClick)
                    )
                }
            }
        }
    }
}

@Composable
private fun TrustedPill(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(BrandTint)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_star),
            contentDescription = stringResource(R.string.cd_trusted_star_icon),
            tint = DeepTeal,
            modifier = Modifier.size(11.dp)
        )
        Text(
            text = stringResource(R.string.contact_details_trusted_pill),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = DeepTeal
        )
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(PureWhite)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Ink
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Subtle
        )
    }
}

@Composable
private fun SharedOutingRow(
    outing: SharedOuting,
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
        IconTile(iconRes = outing.icon.toIconRes(), size = 34.dp, iconSize = 16.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = outing.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Text(
                text = "${outing.dateLabel} · ${stringResource(R.string.journey_details_status_arrived_safely)}",
                fontSize = 12.sp,
                color = Subtle
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

@DrawableRes
private fun SharedOutingIcon.toIconRes(): Int = when (this) {
    SharedOutingIcon.NIGHT_OUT -> R.drawable.ic_moon
    SharedOutingIcon.DINING -> R.drawable.ic_utensils
    SharedOutingIcon.MOVIE -> R.drawable.ic_clapperboard
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ContactDetailsContentPreview() {
    HomeSafeTheme {
        ContactDetailsContent(
            uiState = ContactDetailsSampleData.forId("ahmed"),
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Not trusted")
@Composable
private fun ContactDetailsContentUntrustedPreview() {
    HomeSafeTheme {
        ContactDetailsContent(
            uiState = ContactDetailsSampleData.forId("hamza"),
            onBackClick = {}
        )
    }
}
