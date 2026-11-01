package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal

@Composable
fun RemovableChip(
    text: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    removeContentDescription: String? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(BrandTint)
            .padding(start = 10.dp, top = 5.dp, bottom = 5.dp, end = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = DeepTeal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.widthIn(max = 140.dp)
        )
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .clickable(onClick = onRemove),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = removeContentDescription,
                tint = DeepTeal,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}
