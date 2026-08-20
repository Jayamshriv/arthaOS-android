package com.jayam.artha_os.feature.analytics.domain

import com.jayam.artha_os.feature.ui_models.MerchantSpend
import com.jayam.artha_os.feature.ui_models.MonthlyTrendPoint

data class AnalyticsSnapshot(
    val id: Long = 0,
    val periodType: AnalyticsPeriodType,
    val periodKey: String,
    val totalIncome: Double,
    val totalExpense: Double,
    val topCategory: String?,
    val categoryBreakdown: Map<String, Double>,
    val monthlyTrend: List<MonthlyTrendPoint>,
    val topMerchants: List<MerchantSpend>
)

enum class AnalyticsPeriodType { DAILY, WEEKLY, MONTHLY }