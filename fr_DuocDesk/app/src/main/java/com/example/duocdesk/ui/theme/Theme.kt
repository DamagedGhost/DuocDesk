package com.example.duocdesk.ui.theme


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// 1. Paleta de colores
private val DuocLightColorScheme = lightColorScheme(
    primary = DuocAppPrimary,
    onPrimary = Color.White,
    secondary = DuocAppSecondary,
    onSecondary = Color.Black,
    tertiary = DuocAppButtonGray,
    onTertiary = Color.White,
    background = DuocAppBackground,
    onBackground = Color.Black,
    surface = DuocAppSurface,       // Barras superior/inferior
    onSurface = Color.Black,
    surfaceContainer = DuocAppCard, // Fondo de Tarjetas (blanco)
    onSurfaceVariant = DuocAppTextSecondary, // Texto secundario
    tertiaryContainer = DuocAppSuccess, // Color éxito
    onTertiaryContainer = Color.White // Texto sobre color éxito (no usado aquí)
)

// Tipografía
val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    bodyLarge = TextStyle(fontSize = 16.sp)
)

// Tema de la aplicación usando Material 3
@Composable
fun MVVMMaterialAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DuocLightColorScheme,
        typography = AppTypography,
        content = content
    )
}