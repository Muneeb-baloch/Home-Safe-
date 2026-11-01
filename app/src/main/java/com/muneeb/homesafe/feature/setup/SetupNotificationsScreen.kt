package com.muneeb.homesafe.feature.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.MapBg
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.FeatureRow
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTextButton
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.StepBadge

@Composable
fun SetupNotificationsScreen(
    onBackClick: () -> Unit,
    onTurnOnNotificationsClick: () -> Unit,
    onMaybeLaterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            HomeSafeTopBar(
                onLeadingClick = onBackClick,
                leadingContentDescription = stringResource(R.string.cd_back),
                trailing = { StepBadge(step = 3, totalSteps = 3) }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                HomeSafePrimaryButton(
                    text = stringResource(R.string.setup_turn_on_notifications_button),
                    onClick = onTurnOnNotificationsClick
                )
                HomeSafeTextButton(
                    text = stringResource(R.string.setup_maybe_later_button),
                    onClick = onMaybeLaterClick
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MapBg)
                    .padding(horizontal = 18.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                NotificationPreviewCard(
                    title = stringResource(R.string.setup_notif_sample_name_1),
                    meta = stringResource(R.string.setup_notif_sample_meta_1)
                )
                NotificationPreviewCard(
                    title = stringResource(R.string.setup_notif_sample_name_2),
                    meta = stringResource(R.string.setup_notif_sample_meta_2),
                    modifier = Modifier.alpha(0.55f)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.setup_notifications_heading),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 32.sp,
                    color = Ink
                )
                Text(
                    text = stringResource(R.string.setup_notifications_lead),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 21.sp,
                    color = Muted
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(PureWhite)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                FeatureRow(
                    iconRes = R.drawable.ic_home_stroke,
                    title = stringResource(R.string.setup_feature_arrival_alerts_title),
                    subtitle = stringResource(R.string.setup_feature_arrival_alerts_subtitle)
                )
                FeatureRow(
                    iconRes = R.drawable.ic_bell_outline,
                    title = stringResource(R.string.setup_feature_outing_invites_title),
                    subtitle = stringResource(R.string.setup_feature_outing_invites_subtitle)
                )
            }
        }
    }
}

@Composable
private fun NotificationPreviewCard(
    title: String,
    meta: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(PureWhite)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconTile(
            iconRes = R.drawable.ic_home_stroke,
            background = DeepTeal,
            tint = PureWhite
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Ink)
            Text(text = meta, fontSize = 12.sp, fontWeight = FontWeight.Normal, color = Subtle)
        }
        Text(
            text = stringResource(R.string.setup_notif_now),
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = Faint
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SetupNotificationsScreenPreview() {
    HomeSafeTheme {
        SetupNotificationsScreen(
            onBackClick = {},
            onTurnOnNotificationsClick = {},
            onMaybeLaterClick = {}
        )
    }
}
