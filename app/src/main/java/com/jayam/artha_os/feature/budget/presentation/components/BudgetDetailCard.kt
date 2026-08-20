package com.jayam.artha_os.feature.budget.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.core.ui.theme.Warning
import com.jayam.artha_os.feature.budget.presentation.screens.categoryColor
import com.jayam.artha_os.feature.ui_models.BudgetSummary
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BudgetDetailCard(budget: BudgetSummary, onClick: () -> Unit) {
    val progress = (budget.spent / budget.limit).toFloat().coerceIn(0f, 1f)
    val isExceeded = budget.spent > budget.limit
    val isWarning = progress >= 0.8f && !isExceeded
    val formatter = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }

    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(categoryColor(budget.category), CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(budget.category, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
                }
                if (isExceeded) {
                    Text("Over budget",  color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelMedium)
                } else if (isWarning) {
                    Text("Near limit", color = Warning, style = MaterialTheme.typography.labelMedium)
                }
            }
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = if (isExceeded) MaterialTheme.colorScheme.error else if (isWarning) Warning else MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "${formatter.format(budget.spent)} of ${formatter.format(budget.limit)}",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}