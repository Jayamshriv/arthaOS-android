package com.jayam.artha_os.feature.transaction.presentation.screens

import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.ui_models.TransactionItem

// ── Transactions screen ──────────────────────────────────────────────────
data class TransactionsUiState(
    val transactions: UiState<List<TransactionItem>> = UiState.Loading,
    val searchQuery: String = "",
    val selectedFilter: String = "All"
)