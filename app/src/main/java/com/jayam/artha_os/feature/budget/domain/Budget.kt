package com.jayam.artha_os.feature.budget.domain

data class Budget(
    val id: Long = 0,
    val category: String,
    val limitAmount: Double,
    val spentAmount: Double,
    val month: Int,
    val year: Int,
    val alertThresholdPercent: Int
)