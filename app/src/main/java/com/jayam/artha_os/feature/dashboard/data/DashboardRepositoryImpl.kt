package com.jayam.artha_os.feature.dashboard.data

import com.jayam.artha_os.feature.budget.domain.Budget
import com.jayam.artha_os.feature.dashboard.data.local.CategorySpendRow
import com.jayam.artha_os.feature.dashboard.data.local.DashboardDao
import com.jayam.artha_os.feature.dashboard.domain.DashboardRepository
import com.jayam.artha_os.feature.dashboard.domain.DashboardSummary
import com.jayam.artha_os.feature.transaction.domain.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.jayam.artha_os.feature.budget.data.local.toDomain as budgetToDomain
import com.jayam.artha_os.feature.transaction.data.local.toDomain as txnToDomain

class DashboardRepositoryImpl @Inject constructor(
    private val dao: DashboardDao
) : DashboardRepository {

    override fun getSummary(start: Long, end: Long): Flow<DashboardSummary> =
        dao.getSummary(start, end).map {
            DashboardSummary(it.totalIncome, it.totalExpense, it.transactionCount)
        }

    override fun getTopCategories(start: Long, end: Long, limit: Int): Flow<List<CategorySpendRow>> =
        dao.getTopCategories(start, end, limit).map { list ->
            list.map { CategorySpendRow(it.category, it.totalSpent) }
        }

    override fun getRecentTransactions(limit: Int): Flow<List<Transaction>> =
        dao.getRecentTransactions(limit).map { list -> list.map { it.txnToDomain() } }

    override fun getBudgetsNearLimit(month: Int, year: Int): Flow<List<Budget>> =
        dao.getBudgetsNearLimit(month, year).map { list -> list.map { it.budgetToDomain() } }
}