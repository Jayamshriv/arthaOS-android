package com.jayam.aarthaos.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val ArthaOSShapes = Shapes(
    // Small  — chips, badges, small buttons, input fields
    small  = RoundedCornerShape(8.dp),
    // Medium — transaction list cards, budget progress cards
    medium = RoundedCornerShape(12.dp),
    // Large  — dashboard summary card, bottom sheets, dialog
    large  = RoundedCornerShape(16.dp),
    // ExtraLarge — modal bottom sheets, full-width cards
    extraLarge = RoundedCornerShape(24.dp),
)