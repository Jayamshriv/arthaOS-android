package com.jayam.artha_os.feature.dashboard.domain

import com.jayam.artha_os.feature.budget.domain.Budget
import com.jayam.artha_os.feature.dashboard.data.local.CategorySpendRow
import com.jayam.artha_os.feature.transaction.domain.Transaction
import com.jayam.artha_os.feature.ui_models.CategorySpend
import kotlinx.coroutines.flow.Flow

data class DashboardSummary(
    val totalIncome: Double,
    val totalExpense: Double,
    val transactionCount: Int
)


interface DashboardRepository {
    fun getSummary(start: Long, end: Long): Flow<DashboardSummary>
    fun getTopCategories(start: Long, end: Long, limit: Int = 5): Flow<List<CategorySpendRow>>
    fun getRecentTransactions(limit: Int = 10): Flow<List<Transaction>>
    fun getBudgetsNearLimit(month: Int, year: Int): Flow<List<Budget>>
}