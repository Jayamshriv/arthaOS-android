package com.jayam.artha_os.feature.ui_models

data class AnalyticsData(
    val categoryBreakdown: List<CategorySpend>,
    val monthlyTrend: List<MonthlyTrendPoint>,
    val topMerchants: List<MerchantSpend>
)