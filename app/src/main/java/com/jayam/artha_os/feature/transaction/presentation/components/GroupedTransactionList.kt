package com.jayam.artha_os.feature.transaction.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.feature.dashboard.presentation.components.TransactionRow
import com.jayam.artha_os.feature.ui_models.TransactionItem

@Composable
fun GroupedTransactionList(transactions: List<TransactionItem>) {
    val grouped = remember(transactions) {
        transactions.groupBy { it.dateLabel } // e.g. "Today", "Yesterday", "12 July"
    }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        grouped.forEach { (date, items) ->
            item {
                Text(
                    date,
                    color = Color.White.copy(alpha = 0.4f),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )
            }
            items(items) { TransactionRow(it) }
        }
    }
}