package com.jayam.artha_os.feature.dashboard.presentation.screen

import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.ui_models.AccountBalance
import com.jayam.artha_os.feature.ui_models.BudgetSummary
import com.jayam.artha_os.feature.ui_models.TransactionItem

data class HomeUiState(
    val balance: UiState<AccountBalance> = UiState.Loading,
    val budget: UiState<BudgetSummary> = UiState.Loading,
    val recentTransactions: UiState<List<TransactionItem>> = UiState.Loading
)