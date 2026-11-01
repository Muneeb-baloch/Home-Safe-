package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Border
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.PureWhite

@Composable
fun SelectionCheck(
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(22.dp)
            .clip(CircleShape)
            .then(
                if (selected) {
                    Modifier.background(DeepTeal)
                } else {
                    Modifier.border(2.dp, Border, CircleShape)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = null,
                tint = PureWhite,
                modifier = Modifier.size(13.dp)
            )
        }
    }
}
