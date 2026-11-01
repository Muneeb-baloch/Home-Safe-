package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal

@Composable
fun Pill(
    text: String,
    modifier: Modifier = Modifier,
    background: Color = BrandTint,
    contentColor: Color = DeepTeal,
    dotColor: Color? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (dotColor != null) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
    }
}
