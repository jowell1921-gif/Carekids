package com.example.carekids.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary    = CareKidsBlue,
    secondary  = CareKidsLilac,
    tertiary   = CareKidsMint,
    background = androidx.compose.ui.graphics.Color.White,
    surface    = CareKidsLightPink,
)

private val DarkColorScheme = darkColorScheme(
    primary   = CareKidsBlue,
    secondary = CareKidsLilac,
    tertiary  = CareKidsMint,
)

@Composable
fun CareKidsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = CareKidsTypography,
        content     = content
    )
}
