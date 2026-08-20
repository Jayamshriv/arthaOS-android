package com.jayam.artha_os.feature.analytics.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.feature.ui_models.MerchantSpend
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MerchantRow(merchant: MerchantSpend) {
    val formatter = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(merchant.name, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
            Text(
                "${merchant.transactionCount} transactions",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Text(formatter.format(merchant.totalSpent), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
    }
}