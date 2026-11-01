package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal

@Composable
fun CodeBox(
    code: String,
    onQrClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
    qrContentDescription: String? = null,
    shareContentDescription: String? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BrandTint)
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = code,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 8.sp,
            color = DeepTeal,
            modifier = Modifier.weight(1f)
        )
        CodeBoxIcon(
            iconRes = R.drawable.ic_qr_code,
            contentDescription = qrContentDescription,
            onClick = onQrClick
        )
        CodeBoxIcon(
            iconRes = R.drawable.ic_share,
            contentDescription = shareContentDescription,
            onClick = onShareClick
        )
    }
}

@Composable
private fun CodeBoxIcon(
    iconRes: Int,
    contentDescription: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = DeepTeal,
            modifier = Modifier.size(20.dp)
        )
    }
}
