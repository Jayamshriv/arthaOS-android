package com.jayam.artha_os.feature.budget.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayam.artha_os.core.database.local.entities.BudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(budget: BudgetEntity): Long

    @Delete
    suspend fun delete(budget: BudgetEntity)

    @Query("SELECT * FROM budgets WHERE month = :month AND year = :year")
    fun getBudgetsForMonth(month: Int, year: Int): Flow<List<BudgetEntity>>

    @Query("SELECT * FROM budgets WHERE category = :category AND month = :month AND year = :year LIMIT 1")
    suspend fun getBudgetForCategory(category: String, month: Int, year: Int): BudgetEntity?

    @Query("UPDATE budgets SET spentAmount = spentAmount + :amount WHERE category = :category AND month = :month AND year = :year")
    suspend fun incrementSpent(category: String, month: Int, year: Int, amount: Double)
}