package com.jayam.artha_os.feature.budget.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.budget.domain.Budget
import com.jayam.artha_os.feature.budget.domain.BudgetRepository
import com.jayam.artha_os.feature.ui_models.BudgetSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<BudgetSummary>>>(UiState.Loading)
    val state = _state.asStateFlow()

    // Screen dialog visibility — onAddBudget takes no params, so the actual
    // category/limit input has to be collected inside the composable via this flag.
    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog = _showAddDialog.asStateFlow()

    init { load() }

    private fun load() {
        val now = LocalDate.now()
        repository.getBudgetsForMonth(now.monthValue, now.year)
            .onEach { budgets ->
                _state.value = UiState.Success(
                    budgets.map {
                        BudgetSummary(
                            it.id,
                            it.category,
                            it.spentAmount,
                            it.limitAmount
                        )
                    }
                )
            }
            .catch { e -> _state.value = UiState.Error(e.message ?: "Failed to load budgets") }
            .launchIn(viewModelScope)
    }

    fun onAddBudgetClicked() {
        _showAddDialog.value = true
    }

    fun dismissAddDialog() {
        _showAddDialog.value = false
    }

    fun confirmAddBudget(category: String, limit: Double) {
        viewModelScope.launch {
            val now = LocalDate.now()
            repository.upsertBudget(
                Budget(
                    category = category, limitAmount = limit, spentAmount = 0.0,
                    month = now.monthValue, year = now.year, alertThresholdPercent = 80
                )
            )
            _showAddDialog.value = false
        }
    }

    fun editBudget(summary: BudgetSummary) {
        viewModelScope.launch {
            val now = LocalDate.now()
            repository.upsertBudget(
                Budget(
                    id = summary.id,
                    category = summary.category,
                    limitAmount = summary.limit,
                    spentAmount = summary.spent,
                    month = now.monthValue,
                    year = now.year,
                    alertThresholdPercent = 80
                )
            )
        }
    }

    fun retry() = load()
}