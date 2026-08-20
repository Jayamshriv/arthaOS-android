package com.jayam.artha_os.feature.ui_models

data class MonthlyTrendPoint(
    val monthLabel: String,   // "Jan", "Feb", ...
    val income: Double,
    val expense: Double
)