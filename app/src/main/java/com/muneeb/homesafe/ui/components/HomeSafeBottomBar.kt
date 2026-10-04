package com.muneeb.homesafe.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    )

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp //
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
                            modifier = Modifier.size(20.dp) // Scaled down icon size
                        )
                    },
                    label = {
                        Text(text = destination.title)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        indicatorColor = Color.Transparent // Removes oval pill background
                    )
                )
            }
        }
    }
}