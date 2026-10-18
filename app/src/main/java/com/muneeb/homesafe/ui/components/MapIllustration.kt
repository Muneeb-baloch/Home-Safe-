package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.MapBg
import com.muneeb.homesafe.core.designsystem.theme.PureWhite

@Composable
fun MapIllustration(
    modifier: Modifier = Modifier,
    markerSize: androidx.compose.ui.unit.Dp = 38.dp
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(MapBg)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val lineWidth = 1.dp.toPx()
            val roadColor = DeepTeal.copy(alpha = 0.09f)
            val blockColor = DeepTeal.copy(alpha = 0.06f)

            drawLine(roadColor, Offset(0f, h * 0.28f), Offset(w, h * 0.28f), lineWidth)
            drawLine(roadColor, Offset(0f, h * 0.72f), Offset(w, h * 0.72f), lineWidth)
            drawLine(roadColor, Offset(w * 0.27f, 0f), Offset(w * 0.27f, h), lineWidth)
            drawLine(roadColor, Offset(w * 0.73f, 0f), Offset(w * 0.73f, h), lineWidth)

            val blockY = h * 0.47f
            drawRoundRect(
                color = blockColor,
                topLeft = Offset(0f, blockY),
                size = androidx.compose.ui.geometry.Size(w * 0.2f, 10.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(5.dp.toPx())
            )
            drawRoundRect(
                color = blockColor,
                topLeft = Offset(w - w * 0.2f, blockY),
                size = androidx.compose.ui.geometry.Size(w * 0.2f, 10.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(5.dp.toPx())
            )

            val center = Offset(w / 2f, h / 2f)
            val dashEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f))
            drawCircle(
                color = DeepTeal.copy(alpha = 0.45f),
                radius = minOf(w, h) * 0.3f,
                center = center,
                style = Stroke(width = 1.5.dp.toPx(), pathEffect = dashEffect)
            )
            drawCircle(
                color = DeepTeal.copy(alpha = 0.06f),
                radius = minOf(w, h) * 0.3f,
                center = center
            )
            drawCircle(
                color = DeepTeal.copy(alpha = 0.25f),
                radius = minOf(w, h) * 0.18f,
                center = center,
                style = Stroke(width = 1.5.dp.toPx())
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(markerSize)
                .clip(CircleShape)
                .background(DeepTeal),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_home_stroke),
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(markerSize * 0.52f)
            )
        }
    }
}
