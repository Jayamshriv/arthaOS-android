package com.jayam.artha_os.feature.budget.domain

import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    suspend fun upsertBudget(budget: Budget): Long
    suspend fun deleteBudget(budget: Budget)
    suspend fun getBudgetForCategory(category: String, month: Int, year: Int): Budget?
    suspend fun incrementSpent(category: String, month: Int, year: Int, amount: Double)
    fun getBudgetsForMonth(month: Int, year: Int): Flow<List<Budget>>
}