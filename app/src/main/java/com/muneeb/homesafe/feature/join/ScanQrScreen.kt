package com.muneeb.homesafe.feature.join

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.HomeSafeTheme
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.NearBlack
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.ui.components.CircleIconButton
import com.muneeb.homesafe.ui.components.HomeSafeScreen

@Composable
fun ScanQrScreen(
    onCloseClick: () -> Unit,
    onEnterCodeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeSafeScreen(
        modifier = modifier,
        background = NearBlack,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircleIconButton(
                    iconRes = R.drawable.ic_close,
                    contentDescription = stringResource(R.string.cd_close),
                    onClick = onCloseClick
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.scan_qr_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = PureWhite
                )
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(PureWhite.copy(alpha = 0.12f))
                        .clickable(onClick = {}),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_flashlight),
                        contentDescription = stringResource(R.string.cd_toggle_flashlight),
                        tint = PureWhite,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Button(
                    onClick = onEnterCodeClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PureWhite,
                        contentColor = Ink
                    )
                ) {
                    Text(
                        text = stringResource(R.string.scan_qr_enter_code_instead),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScanViewfinder()

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(R.string.scan_qr_hint),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Normal,
                color = PureWhite.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ScanViewfinder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(240.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val armLength = 28.dp.toPx()
            val strokeWidth = 3.dp.toPx()
            val cornerRadius = 12.dp.toPx()
            val stroke = Stroke(width = strokeWidth, cap = androidx.compose.ui.graphics.StrokeCap.Round)
            val color = PureWhite

            drawLine(color, Offset(0f, armLength), Offset(0f, cornerRadius), strokeWidth, stroke.cap)
            drawLine(color, Offset(cornerRadius, 0f), Offset(armLength, 0f), strokeWidth, stroke.cap)

            drawLine(color, Offset(size.width, armLength), Offset(size.width, cornerRadius), strokeWidth, stroke.cap)
            drawLine(color, Offset(size.width - cornerRadius, 0f), Offset(size.width - armLength, 0f), strokeWidth, stroke.cap)

            drawLine(color, Offset(0f, size.height - armLength), Offset(0f, size.height - cornerRadius), strokeWidth, stroke.cap)
            drawLine(color, Offset(cornerRadius, size.height), Offset(armLength, size.height), strokeWidth, stroke.cap)

            drawLine(color, Offset(size.width, size.height - armLength), Offset(size.width, size.height - cornerRadius), strokeWidth, stroke.cap)
            drawLine(color, Offset(size.width - cornerRadius, size.height), Offset(size.width - armLength, size.height), strokeWidth, stroke.cap)
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_qr_code),
            contentDescription = null,
            tint = PureWhite.copy(alpha = 0.3f),
            modifier = Modifier.size(64.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScanQrScreenPreview() {
    HomeSafeTheme {
        ScanQrScreen(
            onCloseClick = {},
            onEnterCodeClick = {}
        )
    }
}
