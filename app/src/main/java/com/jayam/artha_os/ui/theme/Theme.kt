package com.jayam.artha_os.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// ── Dark colour scheme ─────────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    // Brand
    primary          = Saffron,
    onPrimary        = Color(0xFF4A1E00),
    primaryContainer = Color(0xFF6B3500),
    onPrimaryContainer = Color(0xFFFFDCC2),

    // Secondary — gold accent
    secondary        = Gold,
    onSecondary      = Color(0xFF3D2F00),
    secondaryContainer = Color(0xFF574400),
    onSecondaryContainer = Color(0xFFFFE08A),

    // Tertiary — warm muted (used for income indicator)
    tertiary         = Income,
    onTertiary       = Color(0xFF003918),
    tertiaryContainer = Color(0xFF005224),
    onTertiaryContainer = Color(0xFF8CF5A2),

    // Background & surfaces
    background       = SurfaceBlack,
    onBackground     = TextPrimaryDark,
    surface          = SurfaceDark,
    onSurface        = TextPrimaryDark,
    surfaceVariant   = SurfaceElevated,
    onSurfaceVariant = TextSecondaryDark,

    // Error
    error            = Danger,
    onError          = Color(0xFF690005),
    errorContainer   = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    // Outline
    outline          = SurfaceBorder,
    outlineVariant   = Color(0xFF3A2D24),

    // Inverse
    inverseSurface   = WarmWhite,
    inverseOnSurface = TextPrimaryLight,
    inversePrimary   = Color(0xFF8B4500),

    // Scrim — bottom sheet overlay
    scrim            = Color(0x99000000),
)

// ── Light colour scheme ────────────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary          = Color(0xFF8B4500),
    onPrimary        = Color(0xFFFFFFFF),
    primaryContainer = SaffronLight,
    onPrimaryContainer = Color(0xFF2D1400),

    secondary        = Color(0xFF7A6200),
    onSecondary      = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFF0B3),
    onSecondaryContainer = Color(0xFF251A00),

    tertiary         = Color(0xFF1B6B30),
    onTertiary       = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFB8F5C6),
    onTertiaryContainer = Color(0xFF002109),

    background       = OffWhite,
    onBackground     = TextPrimaryLight,
    surface          = WarmWhite,
    onSurface        = TextPrimaryLight,
    surfaceVariant   = Color(0xFFF5EDE2),
    onSurfaceVariant = TextSecondaryLight,

    error            = Color(0xFFBA1A1A),
    onError          = Color(0xFFFFFFFF),
    errorContainer   = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    outline          = WarmBorder,
    outlineVariant   = Color(0xFFE0D4C6),

    inverseSurface   = SurfaceDark,
    inverseOnSurface = TextPrimaryDark,
    inversePrimary   = Saffron,

    scrim            = Color(0x99000000),
)

// ── Extended colours (not in M3 scheme — custom tokens) ───────────────────
// Access via: LocalArthaColors.current
data class ArthaExtendedColors(
    val income:          Color,
    val expense:         Color,
    val warning:         Color,
    val amountFont:      Color, // monospace amount text colour
    val cardBorder:      Color,
    val saffronDim:      Color, // for icon container backgrounds
    val catFood:         Color,
    val catTravel:       Color,
    val catShopping:     Color,
    val catBills:        Color,
    val catEntertain:    Color,
    val catHealth:       Color,
    val catEducation:    Color,
    val catSalary:       Color,
    val catInvestments:  Color,
    val catOther:        Color,
)

val LocalArthaColors = staticCompositionLocalOf {
    ArthaExtendedColors(
        income         = Income,
        expense        = Expense,
        warning        = Warning,
        amountFont     = Color.Unspecified,
        cardBorder     = Color.Unspecified,
        saffronDim     = SaffronDim,
        catFood        = CatFood,
        catTravel      = CatTravel,
        catShopping    = CatShopping,
        catBills       = CatBills,
        catEntertain   = CatEntertain,
        catHealth      = CatHealth,
        catEducation   = CatEducation,
        catSalary      = CatSalary,
        catInvestments = CatInvestments,
        catOther       = CatOther,
    )
}

private val DarkExtended = ArthaExtendedColors(
    income         = Income,
    expense        = Expense,
    warning        = Warning,
    amountFont     = TextPrimaryDark,
    cardBorder     = SurfaceBorder,
    saffronDim     = SaffronDim,
    catFood        = CatFood,
    catTravel      = CatTravel,
    catShopping    = CatShopping,
    catBills       = CatBills,
    catEntertain   = CatEntertain,
    catHealth      = CatHealth,
    catEducation   = CatEducation,
    catSalary      = CatSalary,
    catInvestments = CatInvestments,
    catOther       = CatOther,
)

private val LightExtended = ArthaExtendedColors(
    income         = Color(0xFF1B6B30),
    expense        = ExpenseL,
    warning        = Color(0xFF7A6200),
    amountFont     = TextPrimaryLight,
    cardBorder     = WarmBorder,
    saffronDim     = Color(0x1AE87B2E),
    catFood        = Color(0xFF2E7D32),
    catTravel      = Color(0xFF1565C0),
    catShopping    = Color(0xFFE65100),
    catBills       = Color(0xFF6A1B9A),
    catEntertain   = Color(0xFFAD1457),
    catHealth      = Color(0xFF00838F),
    catEducation   = Color(0xFF283593),
    catSalary      = Color(0xFF2E7D32),
    catInvestments = Color(0xFFF57F17),
    catOther       = Color(0xFF4E342E),
)

// ── Root theme composable ──────────────────────────────────────────────────
@Composable
fun ArthaOSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic colour is disabled — it would override the saffron identity
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    val extendedColors = if (darkTheme) DarkExtended else LightExtended

    CompositionLocalProvider(LocalArthaColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = ArthaOSTypography,
            shapes      = ArthaOSShapes,
            content     = content,
        )
    }


}

// ── Convenience accessor ───────────────────────────────────────────────────
// Usage anywhere in UI: ArthaTheme.colors.income
object ArthaTheme {
    val colors: ArthaExtendedColors
        @Composable get() = LocalArthaColors.current
}