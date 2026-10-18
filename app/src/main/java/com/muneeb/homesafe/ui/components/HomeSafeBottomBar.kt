package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muneeb.homesafe.core.designsystem.theme.DeepTeal
import com.muneeb.homesafe.core.designsystem.theme.Divider
import com.muneeb.homesafe.core.designsystem.theme.Faint
import com.muneeb.homesafe.core.designsystem.theme.PureWhite
import com.muneeb.homesafe.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSafeBottomBar(
    destinations: List<TopLevelDestination>,
    currentRoute: String?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {

    val primaryRipple = RippleConfiguration(
        color = DeepTeal.copy(alpha = 0.12f)
    )

    NavigationBar(
        modifier = modifier.drawBehind {
            drawLine(
                color = Divider,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 1.dp.toPx()
            )
        },
        containerColor = PureWhite,
        tonalElevation = 0.dp
    ) {
        CompositionLocalProvider(LocalRippleConfiguration provides primaryRipple) {
            destinations.forEach { destination ->
                val isSelected = currentRoute == destination.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        Icon(
                            painter = painterResource(id = destination.iconRes),
                            contentDescription = destination.title,
                            modifier = Modifier.size(21.dp)
                        )
                    },
                    label = {
                        Text(
                            text = destination.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = DeepTeal,
                        selectedTextColor = DeepTeal,
                        unselectedIconColor = Faint,
                        unselectedTextColor = Faint,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
