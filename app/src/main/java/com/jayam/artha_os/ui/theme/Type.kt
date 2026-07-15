package com.jayam.artha_os.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jayam.arthaos.R

// Nunito Sans — warm, rounded, highly legible at small sizes
// Download from fonts.google.com/specimen/Nunito+Sans
// Place in res/font/ as:
//   nunito_sans_regular.ttf   (weight 400)
//   nunito_sans_medium.ttf    (weight 500)
//   nunito_sans_semibold.ttf  (weight 600)
//   nunito_sans_bold.ttf      (weight 700)
val NunitoSans = FontFamily(
    Font(R.font.nunito_sans_regular,  FontWeight.Normal),
    Font(R.font.nunito_sans_regular,   FontWeight.Medium),
    Font(R.font.nunito_sans_regular, FontWeight.SemiBold),
    Font(R.font.nunito_sans_regular,     FontWeight.Bold),
)

// JetBrains Mono — for all rupee amounts, balances, and numbers
// Monospace keeps digits aligned in lists; download from fonts.google.com
// Place as: res/font/jetbrains_mono_regular.ttf
//           res/font/jetbrains_mono_medium.ttf
//           res/font/jetbrains_mono_bold.ttf
val JetBrainsMono = FontFamily(
    Font(R.font.jetbrains_mono_regular, FontWeight.Normal),
    Font(R.font.jetbrains_mono_medium,  FontWeight.Medium),
    Font(R.font.jetbrains_mono_bold,    FontWeight.Bold),
)

val ArthaOSTypography = Typography(

    // ── Display ─ large balance on dashboard ─────────────────────────────
    displayLarge = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.Bold,
        fontSize   = 40.sp,
        lineHeight = 48.sp,
        letterSpacing = (-0.5).sp,
    ),

    // ── Display Medium ─ section totals ──────────────────────────────────
    displayMedium = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.Bold,
        fontSize   = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
    ),

    // ── Display Small ─ card amounts ─────────────────────────────────────
    displaySmall = TextStyle(
        fontFamily = JetBrainsMono,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 22.sp,
        lineHeight = 28.sp,
    ),

    // ── Headline Large ─ screen titles ───────────────────────────────────
    headlineLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Bold,
        fontSize   = 22.sp,
        lineHeight = 28.sp,
    ),

    // ── Headline Medium ─ section headings ───────────────────────────────
    headlineMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 18.sp,
        lineHeight = 24.sp,
    ),

    // ── Headline Small ─ card titles ─────────────────────────────────────
    headlineSmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 16.sp,
        lineHeight = 22.sp,
    ),

    // ── Title Large ─ transaction merchant name ───────────────────────────
    titleLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize   = 16.sp,
        lineHeight = 22.sp,
    ),

    // ── Title Medium ─ list item primary text ────────────────────────────
    titleMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // ── Title Small ─ chip labels, tab labels ────────────────────────────
    titleSmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
    ),

    // ── Body Large ─ descriptions, insight text ──────────────────────────
    bodyLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize   = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),

    // ── Body Medium ─ secondary info, category names ─────────────────────
    bodyMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),

    // ── Body Small ─ timestamps, sub-labels ──────────────────────────────
    bodySmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),

    // ── Label Large ─ buttons ────────────────────────────────────────────
    labelLarge = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // ── Label Medium ─ form field labels ─────────────────────────────────
    labelMedium = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),

    // ── Label Small ─ ALL CAPS section dividers ───────────────────────────
    labelSmall = TextStyle(
        fontFamily = NunitoSans,
        fontWeight = FontWeight.Medium,
        fontSize   = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.8.sp,
    ),
)