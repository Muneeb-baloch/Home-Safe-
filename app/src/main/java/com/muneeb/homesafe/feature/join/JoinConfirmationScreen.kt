package com.muneeb.homesafe.feature.join

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.IconTile
import com.muneeb.homesafe.ui.components.SectionCard

private data class JoinInfoRow(
    val title: String,
    val subtitle: String,
    val shared: Boolean
)

@Composable
fun JoinConfirmationScreen(
    onCloseClick: () -> Unit,
    onChangeHomeZoneClick: () -> Unit,
    onJoinOutingClick: () -> Unit,
    onDeclineClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infoRows = listOf(
        JoinInfoRow(
            title = stringResource(R.string.join_confirm_info_arrival_title),
            subtitle = stringResource(R.string.join_confirm_info_arrival_subtitle),
            shared = true
        ),
        JoinInfoRow(
            title = stringResource(R.string.join_confirm_info_time_title),
            subtitle = stringResource(R.string.join_confirm_info_time_subtitle),
            shared = true
        ),
        JoinInfoRow(
            title = stringResource(R.string.join_confirm_info_location_title),
            subtitle = stringResource(R.string.join_confirm_info_location_subtitle),
            shared = false
        )
    )

    HomeSafeScreen(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 4.dp)
            ) {
                CircleIconButton(
                    iconRes = R.drawable.ic_close,
                    contentDescription = stringResource(R.string.cd_close),
                    onClick = onCloseClick
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                HomeSafePrimaryButton(
                    text = stringResource(R.string.join_confirm_join_button),
                    onClick = onJoinOutingClick
                )
                Text(
                    text = stringResource(R.string.join_confirm_decline_button),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Subtle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = onDeclineClick)
                        .padding(vertical = 4.dp)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionCard(padding = 24.dp) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Avatar(initial = "M", size = 56.dp, fontSize = 20.sp)
                    Text(
                        text = stringResource(R.string.join_confirm_invited_by_format, "Muneeb"),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Subtle
                    )
                    Text(
                        text = stringResource(R.string.join_confirm_outing_name_sample),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Avatar(initial = "M", size = 24.dp, fontSize = 10.sp)
                            Avatar(initial = "A", size = 24.dp, fontSize = 10.sp)
                        }
                        Text(
                            text = stringResource(R.string.join_confirm_members_format, "Muneeb, Ahmed, Ali", "8:00 PM"),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Subtle
                        )
                    }
                }
            }

            SectionCard {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = stringResource(R.string.join_confirm_what_they_see_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink
                    )

                    infoRows.forEach { row ->
                        Row(
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (row.shared) R.drawable.ic_check else R.drawable.ic_close
                                ),
                                contentDescription = null,
                                tint = if (row.shared) DeepTeal else Faint,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                            Column {
                                Text(
                                    text = row.title,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Ink
                                )
                                Text(
                                    text = row.subtitle,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Subtle
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconTile(iconRes = R.drawable.ic_home_stroke)
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(R.string.join_confirm_home_zone_title),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Ink
                            )
                            Text(
                                text = stringResource(R.string.join_confirm_home_zone_subtitle),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                color = Subtle
                            )
                        }
                        Text(
                            text = stringResource(R.string.join_confirm_change_label),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = DeepTeal,
                            modifier = Modifier.clickable(onClick = onChangeHomeZoneClick)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JoinConfirmationScreenPreview() {
    HomeSafeTheme {
        JoinConfirmationScreen(
            onCloseClick = {},
            onChangeHomeZoneClick = {},
            onJoinOutingClick = {},
            onDeclineClick = {}
        )
    }
}
