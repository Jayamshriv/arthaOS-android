package com.jayam.artha_os.feature.dashboard.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.NumberFormat
import java.util.Locale

// ---------- Brand tokens (ArthaOS saffron / near-black identity) ----------

private val Saffron = Color(0xFFFF9933)
private val SaffronDark = Color(0xFFCC7A29)
private val NearBlack = Color(0xFF121212)
private val SurfaceCard = Color(0xFF1E1E1E)
private val PositiveGreen = Color(0xFF4CAF50)
private val NegativeRed = Color(0xFFE5484D)

// ---------- Domain models for this screen ----------

data class AccountBalance(val totalBalance: Double, val accountCount: Int)

data class BudgetSummary(val spent: Double, val limit: Double, val category: String)

data class TransactionItem(
    val id: String,
    val merchant: String,
    val amount: Double,
    val isCredit: Boolean,
    val category: String
)

data class HomeUiState(
    val balance: UiState<AccountBalance> = UiState.Loading,
    val budget: UiState<BudgetSummary> = UiState.Loading,
    val recentTransactions: UiState<List<TransactionItem>> = UiState.Loading
)

// Generic wrapper — same pattern used across every ArthaOS screen
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T, val isRefreshing: Boolean = false) : UiState<T>
    data class Error<T>(val message: String, val cachedData: T? = null) : UiState<T>
}

// ---------- Screen ----------
//
//@Composable
//fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
//    val state by viewModel.uiState.collectAsState()
//    HomeScreenContent(
//        state = state,
//        onRetryBalance = viewModel::refreshBalance,
//        onRetryTransactions = viewModel::refreshTransactions
//    )
//}

@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onRetryBalance: () -> Unit,
    onRetryTransactions: () -> Unit
) {
    Scaffold(containerColor = NearBlack) { padding ->
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
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            val txState = state.recentTransactions
            when (txState) {
                is UiState.Loading -> item { LoadingRow() }
                is UiState.Success -> items(txState.data) { TransactionRow(it) }
                is UiState.Error -> {
                    val cached = txState.cachedData
                    if (cached != null) {
                        items(cached) { TransactionRow(it) }
                        item { ErrorBanner(txState.message, onRetryTransactions) }
                    } else {
                        item { ErrorBanner(txState.message, onRetryTransactions) }
                    }
                }
            }
        }
    }
}

// ---------- Reusable section wrapper (single source of Loading/Error/Success logic) ----------

@Composable
private fun <T> StateSection(
    state: UiState<T>,
    onRetry: () -> Unit,
    successContent: @Composable (data: T, isRefreshing: Boolean) -> Unit
) {
    when (state) {
        is UiState.Loading -> LoadingCard()
        is UiState.Success -> successContent(state.data, state.isRefreshing)
        is UiState.Error -> {
            val cached = state.cachedData
            if (cached != null) {
                Column {
                    successContent(cached, false)
                    Spacer(Modifier.height(8.dp))
                    ErrorBanner(state.message, onRetry)
                }
            } else {
                ErrorCard(state.message, onRetry)
            }
        }
    }
}

// ---------- Pieces ----------

@Composable
private fun BalanceCard(balance: AccountBalance) {
    val formatter = remember(balance) {
        NumberFormat.getCurrencyInstance(Locale("en", "IN"))
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("Total balance", color = Color.White.copy(alpha = 0.6f), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(6.dp))
            Text(
                formatter.format(balance.totalBalance),
                color = Saffron,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "${balance.accountCount} accounts linked",
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun BudgetCard(budget: BudgetSummary) {
    val progress = (budget.spent / budget.limit).toFloat().coerceIn(0f, 1f)
    val formatter = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }

    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(budget.category, color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = if (progress >= 1f) NegativeRed else Saffron,
                trackColor = Color.White.copy(alpha = 0.1f)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "${formatter.format(budget.spent)} of ${formatter.format(budget.limit)}",
                color = Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun TransactionRow(item: TransactionItem) {
    val formatter = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (item.isCredit) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown,
            contentDescription = null,
            tint = if (item.isCredit) PositiveGreen else NegativeRed
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(item.merchant, color = Color.White, style = MaterialTheme.typography.bodyLarge)
            Text(item.category, color = Color.White.copy(alpha = 0.5f), style = MaterialTheme.typography.bodySmall)
        }
        Text(
            (if (item.isCredit) "+" else "-") + formatter.format(item.amount),
            color = if (item.isCredit) PositiveGreen else Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun LoadingCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().height(110.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Saffron, modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
private fun LoadingRow() {
    Box(Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Saffron, modifier = Modifier.size(24.dp))
    }
}

@Composable
private fun ErrorCard(message: String, onRetry: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(message, color = NegativeRed, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            TextButton(onClick = onRetry) { Text("Retry", color = Saffron) }
        }
    }
}

@Composable
private fun ErrorBanner(message: String, onRetry: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NegativeRed.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Showing cached data — $message", color = NegativeRed, style = MaterialTheme.typography.bodySmall)
        TextButton(onClick = onRetry) { Text("Retry", color = Saffron) }
    }
}

// ---------- ViewModel stub (wire to your real repository) ----------

class HomeViewModel {
    val uiState: StateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    fun refreshBalance() {}
    fun refreshTransactions() {}
}

// ---------- Preview ----------

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreenContent(
            state = HomeUiState(
                balance = UiState.Success(AccountBalance(totalBalance = 84320.50, accountCount = 2)),
                budget = UiState.Success(BudgetSummary(spent = 12400.0, limit = 20000.0, category = "Food & dining")),
                recentTransactions = UiState.Success(
                    listOf(
                        TransactionItem("1", "Zomato", 420.0, isCredit = false, category = "Food"),
                        TransactionItem("2", "Salary", 65000.0, isCredit = true, category = "Income"),
                        TransactionItem("3", "Uber", 180.0, isCredit = false, category = "Transport"),
                        TransactionItem("4", "Amazon", 1299.0, isCredit = false, category = "Shopping")
                    )
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
//        HomeScreenContent(
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
//        HomeScreenContent(
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