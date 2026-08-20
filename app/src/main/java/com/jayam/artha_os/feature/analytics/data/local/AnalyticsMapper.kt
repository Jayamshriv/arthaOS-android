package com.jayam.artha_os.feature.analytics.data.local

import com.jayam.artha_os.core.database.local.entities.AnalyticsSnapshotEntity
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType
import com.jayam.artha_os.feature.analytics.domain.AnalyticsSnapshot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType as EntityPeriodType

private val json = Json { ignoreUnknownKeys = true }

fun AnalyticsSnapshotEntity.toDomain() = AnalyticsSnapshot(
    id = id,
    periodType = AnalyticsPeriodType.valueOf(periodType.name),
    periodKey = periodKey,
    totalIncome = totalIncome,
    totalExpense = totalExpense,
    topCategory = topCategory,
    categoryBreakdown = runCatching {
        json.decodeFromString<Map<String, Double>>(categoryBreakdownJson)
    }.getOrDefault(emptyMap())
)

fun AnalyticsSnapshot.toEntity() = AnalyticsSnapshotEntity(
    id = id,
    periodType = EntityPeriodType.valueOf(periodType.name),
    periodKey = periodKey,
    totalIncome = totalIncome,
    totalExpense = totalExpense,
    topCategory = topCategory,
    categoryBreakdownJson = json.encodeToString(categoryBreakdown)
)