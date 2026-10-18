package com.muneeb.homesafe.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

private val baseTypography = Typography()
private val AppTypography = Typography(
    displayLarge = baseTypography.displayLarge.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    displayMedium = baseTypography.displayMedium.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    displaySmall = baseTypography.displaySmall.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    headlineLarge = baseTypography.headlineLarge.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    headlineMedium = baseTypography.headlineMedium.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    headlineSmall = baseTypography.headlineSmall.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    titleLarge = baseTypography.titleLarge.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    titleMedium = baseTypography.titleMedium.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    titleSmall = baseTypography.titleSmall.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    bodyLarge = baseTypography.bodyLarge.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    bodyMedium = baseTypography.bodyMedium.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    bodySmall = baseTypography.bodySmall.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    labelLarge = baseTypography.labelLarge.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    labelMedium = baseTypography.labelMedium.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp),
    labelSmall = baseTypography.labelSmall.copy(fontFamily = InterFontFamily, letterSpacing = 0.sp)
)

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
        typography = AppTypography,
        content = content
    )
}