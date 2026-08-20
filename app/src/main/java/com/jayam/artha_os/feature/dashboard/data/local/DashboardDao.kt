package com.jayam.artha_os.feature.dashboard.data.local

import androidx.room.Dao
import androidx.room.Query
import com.jayam.artha_os.core.database.local.entities.BudgetEntity
import com.jayam.artha_os.core.database.local.entities.TransactionEntity
import com.jayam.artha_os.feature.dashboard.domain.DashboardSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardDao {

    @Query("""
        SELECT 
            COALESCE(SUM(CASE WHEN type = 'CREDIT' THEN amount ELSE 0 END), 0) AS totalIncome,
            COALESCE(SUM(CASE WHEN type = 'DEBIT' THEN amount ELSE 0 END), 0) AS totalExpense,
            COUNT(*) AS transactionCount
        FROM transactions
        WHERE date BETWEEN :start AND :end
    """)
    fun getSummary(start: Long, end: Long): Flow<DashboardSummary>

    @Query("""
    SELECT category, SUM(amount) AS totalSpent
    FROM transactions
    WHERE type = 'DEBIT' AND date BETWEEN :start AND :end AND category IS NOT NULL
    GROUP BY category
    ORDER BY totalSpent DESC
    LIMIT :limit
""")
    fun getTopCategories(start: Long, end: Long, limit: Int = 5): Flow<List<CategorySpendRow>>

    @Query("""
        SELECT * FROM transactions 
        ORDER BY date DESC 
        LIMIT :limit
    """)
    fun getRecentTransactions(limit: Int = 10): Flow<List<TransactionEntity>>

    @Query("""
        SELECT b.* FROM budgets b
        WHERE b.month = :month AND b.year = :year
        AND b.spentAmount >= (b.limitAmount * b.alertThresholdPercent / 100.0)
    """)
    fun getBudgetsNearLimit(month: Int, year: Int): Flow<List<BudgetEntity>>
}
