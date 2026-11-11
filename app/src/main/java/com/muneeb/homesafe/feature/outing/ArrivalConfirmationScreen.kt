package com.muneeb.homesafe.feature.outing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.core.designsystem.theme.Subtle
import com.muneeb.homesafe.ui.components.Avatar
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.HomeSafePrimaryButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen
import com.muneeb.homesafe.ui.components.HomeSafeTextButton
import com.muneeb.homesafe.ui.components.SectionCard

@Composable
fun ArrivalConfirmationScreen(
    uiState: ArrivalConfirmationUiState,
    onCloseClick: () -> Unit,
    onDoneClick: () -> Unit,
    onViewJourneyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val notifiedCompanions = uiState.companions

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
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                HomeSafePrimaryButton(
                    text = stringResource(R.string.arrival_done_button),
                    onClick = onDoneClick
                )
                HomeSafeTextButton(
                    text = stringResource(R.string.arrival_view_journey_button),
                    onClick = onViewJourneyClick
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ArrivalCheckBadge()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.arrival_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Ink
                )
                Text(
                    text = stringResource(R.string.arrival_subtitle_format, uiState.arrivedAtLabel, uiState.outingCode),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Subtle,
                    textAlign = TextAlign.Center
                )
            }

            SectionCard {
                Column {
                    Text(
                        text = stringResource(R.string.arrival_companions_notified_format, notifiedCompanions.size),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Ink,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    notifiedCompanions.forEachIndexed { index, companion ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Avatar(initial = companion.name.take(1).uppercase(), size = 32.dp, fontSize = 13.sp)
                            Text(
                                text = companion.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Ink,
                                modifier = Modifier.weight(1f)
                            )
                            if (companion.delivered) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_check),
                                        contentDescription = null,
                                        tint = DeepTeal,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = stringResource(R.string.arrival_delivered_label),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = DeepTeal,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            } else {
                                Text(
                                    text = stringResource(R.string.arrival_sending_label),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Subtle
                                )
                            }
                        }
                        if (index != notifiedCompanions.lastIndex) {
                            HorizontalDivider(color = Divider, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArrivalCheckBadge(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(vertical = 12.dp)
            .size(112.dp)
            .clip(CircleShape)
            .background(BrandTint),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(DeepTeal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ArrivalConfirmationScreenPreview() {
    HomeSafeTheme {
        ArrivalConfirmationScreen(
            uiState = ArrivalConfirmationUiState(
                outingCode = "7K4P",
                arrivedAtLabel = "11:42 PM",
                companions = listOf(
                    NotifiedCompanionUi(uid = "1", name = "Ahmed", delivered = true),
                    NotifiedCompanionUi(uid = "2", name = "Ali", delivered = true),
                    NotifiedCompanionUi(uid = "3", name = "Hamza", delivered = false)
                )
            ),
            onCloseClick = {},
            onDoneClick = {},
            onViewJourneyClick = {}
        )
    }
}
