package com.jayam.artha_os.feature.analytics.domain


data class AnalyticsSnapshot(
    val id: Long = 0,
    val periodType: AnalyticsPeriodType,
    val periodKey: String,
    val totalIncome: Double,
    val totalExpense: Double,
    val topCategory: String?,
    val categoryBreakdown: Map<String, Double>
)

enum class AnalyticsPeriodType { DAILY, WEEKLY, MONTHLY }