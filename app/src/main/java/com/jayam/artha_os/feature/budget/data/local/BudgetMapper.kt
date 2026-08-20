package com.jayam.artha_os.feature.budget.data.local

import com.jayam.artha_os.core.database.local.entities.BudgetEntity
import com.jayam.artha_os.feature.budget.domain.Budget

fun BudgetEntity.toDomain() = Budget(
    id = id, category = category, limitAmount = limitAmount,
    spentAmount = spentAmount, month = month, year = year,
    alertThresholdPercent = alertThresholdPercent
)

fun Budget.toEntity() = BudgetEntity(
    id = id, category = category, limitAmount = limitAmount,
    spentAmount = spentAmount, month = month, year = year,
    alertThresholdPercent = alertThresholdPercent
)