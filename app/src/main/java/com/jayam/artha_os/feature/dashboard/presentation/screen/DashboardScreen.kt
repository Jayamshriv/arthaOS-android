package com.jayam.artha_os.feature.dashboard.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.core.ui.theme.ArthaOSTheme
import com.jayam.artha_os.core.ui.ui_utils.ErrorBanner
import com.jayam.artha_os.core.ui.ui_utils.LoadingRow
import com.jayam.artha_os.core.ui.ui_utils.StateSection
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.dashboard.presentation.components.BalanceCard
import com.jayam.artha_os.feature.dashboard.presentation.components.BudgetCard
import com.jayam.artha_os.feature.dashboard.presentation.components.TransactionRow
import com.jayam.artha_os.feature.ui_models.AccountBalance
import com.jayam.artha_os.feature.ui_models.BudgetSummary

@Composable
fun DashboardScreen(state: HomeUiState,onRetryBalance: () -> Unit, onRetryTransactions: () -> Unit?) {
    Scaffold(    containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    "Good morning, Johnny",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            item {
                StateSection(state.balance, onRetryBalance) { balance, _ ->
                    BalanceCard(balance)
                }
            }

            item {
                StateSection(state.budget, onRetryBalance) { budget, _ ->
                    BudgetCard(budget)
                }
            }

            item {
                Text(
                    "Recent transactions",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }

            val txState = state.recentTransactions
            when (txState) {
                is UiState.Loading -> item { LoadingRow() }
                is UiState.Success -> items(txState.data) { TransactionRow(it) }
                is UiState.Error -> {
//                    val cached = txState.cachedData
//                    if (cached != null) {
//                        items(cached) { TransactionRow(it) }
//                        item { ErrorBanner(txState.message, onRetryTransactions) }
//                    } else {
                        item { ErrorBanner(txState.message, onRetryTransactions) }
//                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun HomeScreenPreview() {
    ArthaOSTheme(true) {
        DashboardScreen(
            state = HomeUiState(
                balance = UiState.Success(AccountBalance(totalBalance = 84320.50, accountCount = 2)),
                budget = UiState.Success(
                    BudgetSummary(
                        spent = 19400.0,
                        limit = 20000.0,
                        category = "Food & dining"
                    )
                ),
                recentTransactions = UiState.Error(
                    message = "No internet connection",
//                    cachedData = listOf(
//                        TransactionItem("1", "Zomato", amount = 420.0, isCredit = false, category = "Food")
//                    )
                )
            ),
            onRetryBalance = {},
            onRetryTransactions = {}
        )
    }
}
//
//@Preview(showBackground = true, backgroundColor = 0xFF121212)
//@Composable
//private fun HomeScreenLoadingPreview() {
//    MaterialTheme {
//        DashboardScreen(
//            state = HomeUiState(),
//            onRetryBalance = {},
//            onRetryTransactions = {}
//        )
//    }
//}

//@Preview(showBackground = true, backgroundColor = 0xFF121212)
//@Composable
//private fun HomeScreenErrorWithCachePreview() {
//    MaterialTheme {
//        DashboardScreen(
//            state = HomeUiState(
//                balance = UiState.Success(AccountBalance(totalBalance = 84320.50, accountCount = 2)),
//                budget = UiState.Success(BudgetSummary(spent = 19400.0, limit = 20000.0, category = "Food & dining")),
//                recentTransactions = UiState.Error(
//                    message = "No internet connection",
//                    cachedData = listOf(
//                        TransactionItem("1", "Zomato", 420.0, isCredit = false, category = "Food")
//                    )
//                )
//            ),
//            onRetryBalance = {},
//            onRetryTransactions = {}
//        )
//    }
//}