package com.muneeb.homesafe.feature.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Muted
import com.muneeb.homesafe.core.designsystem.theme.Page
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeTopBar
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.MapIllustration
import com.muneeb.homesafe.ui.components.SelectableChip

@Composable
fun SetupHomeLocationScreen(
    onBackClick: () -> Unit,
    onUseCurrentLocationClick: () -> Unit,
    onEditAddressClick: () -> Unit,
    onSaveHomeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedRadiusMeters by remember { mutableIntStateOf(150) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Page)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        HomeSafeTopBar(
            onLeadingClick = onBackClick,
            leadingContentDescription = stringResource(R.string.cd_back),
            title = stringResource(R.string.setup_home_title)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Subtle,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = stringResource(R.string.setup_search_address_hint),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Subtle
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
            ) {
                MapIllustration(modifier = Modifier.fillMaxSize())

                Text(
                    text = stringResource(R.string.setup_map_drag_hint),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = Muted,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp)
                        .clip(RoundedCornerShape(50))
                        .background(PureWhite)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )

                CircleIconButton(
                    iconRes = R.drawable.ic_locate,
                    contentDescription = stringResource(R.string.cd_use_current_location),
                    onClick = onUseCurrentLocationClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(PureWhite)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconTile(iconRes = R.drawable.ic_home_stroke, size = 38.dp)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.setup_home_label),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )
                    Text(
                        text = stringResource(R.string.setup_home_address_sample),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Subtle
                    )
                }
                Text(
                    text = stringResource(R.string.setup_edit),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = DeepTeal,
                    modifier = Modifier.clickable(onClick = onEditAddressClick)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(PureWhite)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.setup_arrival_zone_label),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "$selectedRadiusMeters m",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = DeepTeal
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SelectableChip(
                        text = stringResource(R.string.setup_radius_100),
                        selected = selectedRadiusMeters == 100,
                        onClick = { selectedRadiusMeters = 100 },
                        modifier = Modifier.weight(1f)
                    )
                    SelectableChip(
                        text = stringResource(R.string.setup_radius_150),
                        selected = selectedRadiusMeters == 150,
                        onClick = { selectedRadiusMeters = 150 },
                        modifier = Modifier.weight(1f)
                    )
                    SelectableChip(
                        text = stringResource(R.string.setup_radius_250),
                        selected = selectedRadiusMeters == 250,
                        onClick = { selectedRadiusMeters = 250 },
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = stringResource(R.string.setup_radius_hint),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Subtle
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            HomeSafePrimaryButton(
                text = stringResource(R.string.setup_save_as_home_button),
                onClick = onSaveHomeClick
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SetupHomeLocationScreenPreview() {
    HomeSafeTheme {
        SetupHomeLocationScreen(
            onBackClick = {},
            onUseCurrentLocationClick = {},
            onEditAddressClick = {},
            onSaveHomeClick = {}
        )
    }
}
