package com.jayam.artha_os.feature.budget.data

import com.jayam.artha_os.feature.budget.data.local.BudgetDao
import com.jayam.artha_os.feature.budget.data.local.toDomain
import com.jayam.artha_os.feature.budget.data.local.toEntity
import com.jayam.artha_os.feature.budget.domain.Budget
import com.jayam.artha_os.feature.budget.domain.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val dao: BudgetDao
) : BudgetRepository {

    override suspend fun upsertBudget(budget: Budget): Long = dao.upsert(budget.toEntity())

    override suspend fun deleteBudget(budget: Budget) = dao.delete(budget.toEntity())

    override suspend fun getBudgetForCategory(category: String, month: Int, year: Int): Budget? =
        dao.getBudgetForCategory(category, month, year)?.toDomain()

    override suspend fun incrementSpent(category: String, month: Int, year: Int, amount: Double) =
        dao.incrementSpent(category, month, year, amount)

    override fun getBudgetsForMonth(month: Int, year: Int): Flow<List<Budget>> =
        dao.getBudgetsForMonth(month, year).map { list -> list.map { it.toDomain() } }
}