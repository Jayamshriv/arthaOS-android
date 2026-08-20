package com.jayam.artha_os.core.ui.common_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun LoadingCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(110.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
        }
    }
}

fun PaddingValues.withoutTopPadding(): PaddingValues {
    return object : PaddingValues {
        override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
            return this@withoutTopPadding.calculateLeftPadding(layoutDirection)
        }

        override fun calculateTopPadding(): Dp {
            return 0.dp // Hardcode top padding to zero
        }

        override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
            return this@withoutTopPadding.calculateRightPadding(layoutDirection)
        }

        override fun calculateBottomPadding(): Dp {
            return this@withoutTopPadding.calculateBottomPadding()
        }
    }
}