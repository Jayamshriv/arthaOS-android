package com.jayam.artha_os.feature.transaction.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.core.ui.common_components.withoutTopPadding
import com.jayam.artha_os.core.ui.theme.ArthaOSTheme
import com.jayam.artha_os.core.ui.theme.ArthaTheme
import com.jayam.artha_os.core.ui.ui_utils.StateSection
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.budget.presentation.components.EmptyState
import com.jayam.artha_os.feature.transaction.presentation.TransactionFilters
import com.jayam.artha_os.feature.transaction.presentation.components.GroupedTransactionList
import com.jayam.artha_os.feature.transaction.presentation.sampleTransactions

@Composable
fun TransactionsScreenContent(
    state: TransactionsUiState,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(    containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding.withoutTopPadding())
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        "Search transactions",
                        color = Color.White.copy(alpha = 0.4f)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        null,
                        tint = Color.White.copy(alpha = 0.5f)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                    focusedBorderColor  = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    cursorColor  = MaterialTheme.colorScheme.primary,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()

            )

            // Category filter chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TransactionFilters.categories) { filter ->
                    FilterChip(
                        selected = filter == selectedFilter,
                        onClick = { onFilterSelected(filter) },
                        label = { Text(filter) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            labelColor = Color.White.copy(alpha = 0.7f),
                            selectedContainerColor = ArthaTheme.colors.saffronDim,
                            selectedLabelColor  = MaterialTheme.colorScheme.primary
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = filter == selectedFilter,
                            borderColor = Color.White.copy(alpha = 0.1f),
                            selectedBorderColor  = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // Grouped transaction list
            StateSection(state.transactions, onRetry) { data, isRefreshing ->
                if (data.isEmpty()) {
                    EmptyState(
                        icon = Icons.AutoMirrored.Filled.ReceiptLong,
                        title = "No transactions found",
                        description = "Try a different filter or search term"
                    )
                } else {
                    GroupedTransactionList(data)
                }
            }
        }
    }
}


// ── Transactions ─────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun TransactionsScreenPreview() {
    ArthaOSTheme(true) {
        TransactionsScreenContent(
            state = TransactionsUiState(
                transactions = UiState.Success(sampleTransactions),
                selectedFilter = "All"
            ),
            selectedFilter = "All",
            onFilterSelected = {},
            onSearchQueryChange = {},
            onRetry = {}
        )
    }
}

@Preview(name = "Transactions - Empty", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun TransactionsScreenEmptyPreview() {
    ArthaOSTheme(true) {
        TransactionsScreenContent(
            state = TransactionsUiState(transactions = UiState.Success(emptyList())),
            selectedFilter = "Food",
            onFilterSelected = {},
            onSearchQueryChange = {},
            onRetry = {}
        )
    }
}

@Preview(name = "Transactions - Error", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun TransactionsScreenErrorPreview() {
    ArthaOSTheme(true) {
        TransactionsScreenContent(
            state = TransactionsUiState(
                transactions = UiState.Error("Couldn't load transactions"/*, cachedData = null*/)
            ),
            selectedFilter = "All",
            onFilterSelected = {},
            onSearchQueryChange = {},
            onRetry = {}
        )
    }
}

