package com.jayam.artha_os.feature.ui_models

data class BudgetSummary(
    val id: Long = 0L,
    val category: String,
    val spent: Double,
    val limit: Double
)