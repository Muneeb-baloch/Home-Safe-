package com.muneeb.homesafe.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.R
import com.muneeb.homesafe.core.designsystem.theme.Ink

@Composable
fun HomeSafeTopBar(
    onLeadingClick: () -> Unit,
    leadingContentDescription: String?,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIconRes: Int = R.drawable.ic_chevron_left,
    title: String? = null,
    trailing: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleIconButton(
            iconRes = leadingIconRes,
            contentDescription = leadingContentDescription,
            onClick = onLeadingClick
        )

        if (title != null) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        trailing()
    }
}
