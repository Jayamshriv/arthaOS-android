package com.jayam.artha_os.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.core.database.local.helper.TransactionType
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.budget.domain.BudgetRepository
import com.jayam.artha_os.feature.dashboard.domain.DashboardRepository
import com.jayam.artha_os.feature.dashboard.presentation.screen.HomeUiState
import com.jayam.artha_os.feature.ui_models.AccountBalance
import com.jayam.artha_os.feature.ui_models.BudgetSummary
import com.jayam.artha_os.feature.ui_models.TransactionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        loadBalance()
        loadBudget()
        loadRecentTransactions()
    }

    private fun currentMonthRange(): Pair<Long, Long> {
        val zone = ZoneId.systemDefault()
        val ym = YearMonth.now()
        val start = ym.atDay(1).atStartOfDay(zone).toInstant().toEpochMilli()
        val end = ym.atEndOfMonth().atTime(23, 59, 59).atZone(zone).toInstant().toEpochMilli()
        return start to end
    }

    private fun loadBalance() {
        val (start, end) = currentMonthRange()
        dashboardRepository.getSummary(start, end)
            .onEach { summary ->
                _state.value = _state.value.copy(
                    balance = UiState.Success(
                        AccountBalance(totalBalance = summary.totalIncome - summary.totalExpense, accountCount = summary.transactionCount)
                    )
                )
            }
            .catch { e ->
                _state.value = _state.value.copy(
                    balance = UiState.Error(e.message ?: "Failed to load balance")
                )
            }
            .launchIn(viewModelScope)
    }

    private fun loadBudget() {
        val now = LocalDate.now()
        budgetRepository.getBudgetsForMonth(now.monthValue, now.year)
            .onEach { budgets ->
                // surfaces the budget closest to its limit — most actionable one for the dashboard
                val primary = budgets.maxByOrNull { it.spentAmount / it.limitAmount.coerceAtLeast(1.0) }
                _state.value = _state.value.copy(
                    budget = if (primary != null) {
                        UiState.Success(BudgetSummary(
                            id = primary.id , spent =  primary.spentAmount, category =  primary.category,
                            limit =primary.limitAmount
                        ))
                    } else {
                        UiState.Error("No budget set for this month")
                    }
                )
            }
            .catch { e -> _state.value = _state.value.copy(budget = UiState.Error(e.message ?: "Failed to load budget")) }
            .launchIn(viewModelScope)
    }

    private fun loadRecentTransactions() {
        dashboardRepository.getRecentTransactions(limit = 10)
            .onEach { transactions ->
                _state.value = _state.value.copy(
                    recentTransactions = UiState.Success(
                        transactions.map {
                            TransactionItem(
                                id = it.id.toString(),
                                name = it.merchantName ?: it.description,
                                amount = it.amount,
                                isCredit = it.type == TransactionType.CREDIT,
                                category = it.category ?: "Uncategorized"
                            )
                        }
                    )
                )
            }
            .catch { e ->
                val cached = (_state.value.recentTransactions as? UiState.Success)?.data
                _state.value = _state.value.copy(
                    recentTransactions = UiState.Error(e.message ?: "No internet connection", cached)
                )
            }
            .launchIn(viewModelScope)
    }

    fun retryBalance() = loadBalance()
    fun retryTransactions() = loadRecentTransactions()
}
