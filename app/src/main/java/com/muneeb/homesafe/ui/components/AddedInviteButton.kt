package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.PureWhite

@Composable
fun AddedInviteButton(
    added: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .then(
                if (added) {
                    Modifier.background(DeepTeal)
                } else {
                    Modifier
                        .background(PureWhite)
                        .border(BorderStroke(1.dp, DeepTeal), RoundedCornerShape(50))
                }
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Icon(
            painter = painterResource(id = if (added) R.drawable.ic_check else R.drawable.ic_plus),
            contentDescription = null,
            tint = if (added) PureWhite else DeepTeal,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (added) PureWhite else DeepTeal
        )
    }
}
