package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Ink
import com.muneeb.homesafe.core.designsystem.theme.Subtle

@Composable
fun ContactRow(
    initial: String,
    name: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    avatarBackground: Color = BrandTint,
    avatarContentColor: Color = DeepTeal,
    onClick: (() -> Unit)? = null,
    trailing: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Avatar(
            initial = initial,
            background = avatarBackground,
            contentColor = avatarContentColor
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Ink,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = Subtle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        trailing()
    }
}
