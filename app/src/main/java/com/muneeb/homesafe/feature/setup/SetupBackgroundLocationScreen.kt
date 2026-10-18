package com.muneeb.homesafe.feature.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.Page
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeTextButton
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.RadioOptionRow
import com.muneeb.homesafe.ui.components.StepBadge

@Composable
fun SetupBackgroundLocationScreen(
    onBackClick: () -> Unit,
    onOpenSettingsClick: () -> Unit,
    onNotNowClick: () -> Unit,
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
            trailing = { StepBadge(step = 2, totalSteps = 3) }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.setup_background_heading),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 32.sp,
                    color = Ink
                )
                Text(
                    text = stringResource(R.string.setup_background_lead),
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
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.setup_background_permission_label),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Faint
                )
                RadioOptionRow(
                    label = stringResource(R.string.setup_option_allow_always),
                    selected = true,
                    badgeText = stringResource(R.string.setup_option_required)
                )
                RadioOptionRow(label = stringResource(R.string.setup_option_allow_foreground), selected = false)
                RadioOptionRow(label = stringResource(R.string.setup_option_ask_every_time), selected = false)
                RadioOptionRow(label = stringResource(R.string.setup_option_dont_allow), selected = false)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(BrandTint)
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info_circle),
                    contentDescription = null,
                    tint = DeepTeal,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(R.string.setup_background_hint),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 19.sp,
                    color = DeepTeal
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HomeSafePrimaryButton(
                text = stringResource(R.string.setup_open_settings_button),
                onClick = onOpenSettingsClick
            )
            HomeSafeTextButton(
                text = stringResource(R.string.setup_not_now_button),
                onClick = onNotNowClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SetupBackgroundLocationScreenPreview() {
    HomeSafeTheme {
        SetupBackgroundLocationScreen(
            onBackClick = {},
            onOpenSettingsClick = {},
            onNotNowClick = {}
        )
    }
}
