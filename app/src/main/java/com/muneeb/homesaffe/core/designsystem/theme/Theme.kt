package com.muneeb.homesaffe.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = DeepTeal,
    onPrimary = PureWhite,
    primaryContainer = PaleTeal,
    onPrimaryContainer = DarkTeal,

    background = WarmWhite,
    onBackground = NearBlack,

    surface = PureWhite,
    onSurface = NearBlack,
    surfaceVariant = PaleTeal,
    onSurfaceVariant = SlateGray,

    outline = SoftGray,
    error = ErrorRed,
    onError = PureWhite
)

@Composable
fun HomeSafeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}