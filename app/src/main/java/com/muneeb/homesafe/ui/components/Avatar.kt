package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal

@Composable
fun Avatar(
    initial: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    background: Color = BrandTint,
    contentColor: Color = DeepTeal,
    fontSize: TextUnit = 15.sp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            fontSize = fontSize,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
    }
}
