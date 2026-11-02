package com.muneeb.homesafe.feature.profile

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.DestructiveRed
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.HomeSafeOutlineButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.SectionCard
import com.muneeb.homesafe.ui.components.Toggle

@Composable
fun ProfileContent(
    uiState: ProfileUiState,
    onEditProfileClick: () -> Unit = {},
    onHomeLocationClick: () -> Unit = {},
    onArrivalAlertsChange: (Boolean) -> Unit = {},
    onOutingInvitesChange: (Boolean) -> Unit = {},
    onTrustedContactsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            Text(
                text = stringResource(R.string.profile_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Ink,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 10.dp)
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(key = "header") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Avatar(
                        initial = uiState.name.take(1),
                        size = 72.dp,
                        fontSize = 26.sp
                    )
                    Text(
                        text = uiState.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                    Text(
                        text = uiState.email,
                        fontSize = 13.sp,
                        color = Muted
                    )
                    Text(
                        text = uiState.phone,
                        fontSize = 13.sp,
                        color = Muted
                    )
                    HomeSafeOutlineButton(
                        text = stringResource(R.string.profile_edit_button),
                        onClick = onEditProfileClick,
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth()
                    )
                }
            }

            item(key = "home_safety") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SectionLabel(text = stringResource(R.string.profile_home_safety_section))
                    SectionCard(padding = 0.dp) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            ChevronRow(
                                iconRes = R.drawable.ic_home_stroke,
                                title = stringResource(R.string.profile_home_location_title),
                                subtitle = stringResource(
                                    R.string.profile_home_location_subtitle_format,
                                    uiState.homeAddress,
                                    uiState.homeRadiusLabel
                                ),
                                onClick = onHomeLocationClick,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }

            item(key = "notifications") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SectionLabel(text = stringResource(R.string.profile_notifications_section))
                    SectionCard(padding = 0.dp) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            ToggleRow(
                                iconRes = R.drawable.ic_home_stroke,
                                title = stringResource(R.string.setup_feature_arrival_alerts_title),
                                subtitle = stringResource(R.string.setup_feature_arrival_alerts_subtitle),
                                checked = uiState.arrivalAlertsEnabled,
                                onCheckedChange = onArrivalAlertsChange,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                            HorizontalDivider(color = Divider, thickness = 1.dp)
                            ToggleRow(
                                iconRes = R.drawable.ic_bell_outline,
                                title = stringResource(R.string.setup_feature_outing_invites_title),
                                subtitle = stringResource(R.string.setup_feature_outing_invites_subtitle),
                                checked = uiState.outingInvitesEnabled,
                                onCheckedChange = onOutingInvitesChange,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }

            item(key = "account") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SectionLabel(text = stringResource(R.string.profile_account_section))
                    SectionCard(padding = 0.dp) {
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            ChevronRow(
                                iconRes = R.drawable.ic_companions_stroke,
                                title = stringResource(R.string.profile_trusted_contacts_title),
                                subtitle = stringResource(R.string.profile_trusted_contacts_subtitle),
                                onClick = onTrustedContactsClick,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                            HorizontalDivider(color = Divider, thickness = 1.dp)
                            ChevronRow(
                                iconRes = R.drawable.ic_lock,
                                title = stringResource(R.string.profile_privacy_title),
                                subtitle = stringResource(R.string.profile_privacy_subtitle),
                                onClick = onPrivacyClick,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                            HorizontalDivider(color = Divider, thickness = 1.dp)
                            ChevronRow(
                                iconRes = R.drawable.ic_help_circle,
                                title = stringResource(R.string.profile_help_title),
                                subtitle = stringResource(R.string.profile_help_subtitle),
                                onClick = onHelpClick,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }

            item(key = "sign_out") {
                Text(
                    text = stringResource(R.string.profile_sign_out),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = DestructiveRed,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable(onClick = onSignOutClick)
                )
            }

            item(key = "version") {
                Text(
                    text = stringResource(R.string.profile_app_version),
                    fontSize = 11.sp,
                    color = Faint,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionLabel(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Subtle,
        modifier = modifier
    )
}

@Composable
private fun ChevronRow(
    @DrawableRes iconRes: Int,
    title: String,
    subtitle: String,
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
        IconTile(iconRes = iconRes)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Ink)
            Text(text = subtitle, fontSize = 12.sp, color = Subtle)
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
private fun ToggleRow(
    @DrawableRes iconRes: Int,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconTile(iconRes = iconRes)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Ink)
            Text(text = subtitle, fontSize = 12.sp, color = Subtle)
        }
        Toggle(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileContentPreview() {
    HomeSafeTheme {
        ProfileContent(uiState = ProfileUiState())
    }
}
