package com.jayam.artha_os.feature.transaction.presentation

import com.jayam.artha_os.feature.ui_models.AnalyticsData
import com.jayam.artha_os.feature.ui_models.BudgetSummary
import com.jayam.artha_os.feature.ui_models.CategorySpend
import com.jayam.artha_os.feature.ui_models.MerchantSpend
import com.jayam.artha_os.feature.ui_models.MonthlyTrendPoint
import com.jayam.artha_os.feature.ui_models.TransactionItem

// ── Sample data ──────────────────────────────────────────────────────────
val sampleTransactions = listOf(
    TransactionItem("1", "Swiggy", "Food", 486.0, false, "Today", 0L),
    TransactionItem("2", "Salary - Acme Corp", "Salary", 85000.0, true, "Today", 0L),
    TransactionItem("3", "Uber", "Travel", 212.0, false, "Yesterday", 0L),
    TransactionItem("4", "Amazon", "Shopping", 1899.0, false, "Yesterday", 0L),
    TransactionItem("5", "Electricity Bill", "Bills", 1450.0, false, "12 July", 0L),
    TransactionItem("6", "Zerodha", "Investments", 5000.0, false, "12 July", 0L)
)

val sampleBudgets = listOf(
    BudgetSummary("1", "Food", 3200.0, 5000.0),
    BudgetSummary("2", "Shopping", 5600.0, 4000.0),
    BudgetSummary("3", "Travel", 1800.0, 3000.0),
    BudgetSummary("4", "Bills", 2900.0, 3000.0),
    BudgetSummary("5", "Entertainment", 900.0, 2000.0)
)

val sampleAnalytics = AnalyticsData(
    categoryBreakdown = listOf(
        CategorySpend("Food", 3200.0, 32.0F),
        CategorySpend("Shopping", 2800.0, 28.0f),
        CategorySpend("Bills", 2100.0, 21.0f),
        CategorySpend("Travel", 1200.0, 12.0f),
        CategorySpend("Entertainment", 700.0, 7.0f)
    ),
    monthlyTrend = listOf(
        MonthlyTrendPoint("Feb", 82000.0, 61000.0),
        MonthlyTrendPoint("Mar", 85000.0, 58000.0),
        MonthlyTrendPoint("Apr", 85000.0, 67000.0),
        MonthlyTrendPoint("May", 90000.0, 62000.0),
        MonthlyTrendPoint("Jun", 85000.0, 71000.0),
        MonthlyTrendPoint("Jul", 85000.0, 64000.0)
    ),
    topMerchants = listOf(
        MerchantSpend("Amazon", 8400.0, 12),
        MerchantSpend("Swiggy", 5200.0, 24),
        MerchantSpend("Uber", 3100.0, 18)
    )
)