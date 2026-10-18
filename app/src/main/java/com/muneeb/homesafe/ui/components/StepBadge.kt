package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.BrandTint
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal

@Composable
fun StepBadge(
    step: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.setup_step_format, step, totalSteps),
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = DeepTeal,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(BrandTint)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}
