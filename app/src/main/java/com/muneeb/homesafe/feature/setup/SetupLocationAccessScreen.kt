package com.muneeb.homesafe.feature.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.Page
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.ui.components.FeatureRow
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.MapIllustration
import com.muneeb.homesafe.ui.components.StepBadge

@Composable
fun SetupLocationAccessScreen(
    onBackClick: () -> Unit,
    onAllowLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Page)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        HomeSafeTopBar(
            onLeadingClick = onBackClick,
            leadingContentDescription = stringResource(R.string.cd_back),
            trailing = { StepBadge(step = 1, totalSteps = 3) }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MapIllustration(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.setup_location_heading),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 32.sp,
                    color = Ink
                )
                Text(
                    text = stringResource(R.string.setup_location_lead),
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
                    iconRes = R.drawable.ic_shield_check,
                    title = stringResource(R.string.setup_feature_arrival_only_title),
                    subtitle = stringResource(R.string.setup_feature_arrival_only_subtitle)
                )
                FeatureRow(
                    iconRes = R.drawable.ic_phone_notification,
                    title = stringResource(R.string.setup_feature_background_title),
                    subtitle = stringResource(R.string.setup_feature_background_subtitle)
                )
                FeatureRow(
                    iconRes = R.drawable.ic_battery,
                    title = stringResource(R.string.setup_feature_battery_title),
                    subtitle = stringResource(R.string.setup_feature_battery_subtitle)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeSafePrimaryButton(
                text = stringResource(R.string.setup_allow_location_button),
                onClick = onAllowLocationClick
            )

            Text(
                text = stringResource(R.string.setup_location_footnote),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Faint,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SetupLocationAccessScreenPreview() {
    HomeSafeTheme {
        SetupLocationAccessScreen(onBackClick = {}, onAllowLocationClick = {})
    }
}
