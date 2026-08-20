package com.jayam.artha_os.feature.analytics.data.local

import com.jayam.artha_os.core.database.local.entities.AnalyticsSnapshotEntity
import com.jayam.artha_os.feature.analytics.domain.AnalyticsSnapshot
import com.jayam.artha_os.feature.ui_models.MerchantSpend
import com.jayam.artha_os.feature.ui_models.MonthlyTrendPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType as EntityPeriodType

private val json = Json { ignoreUnknownKeys = true }

fun AnalyticsSnapshotEntity.toDomain() = AnalyticsSnapshot(
    id = id,
    periodType = EntityPeriodType.valueOf(periodType.name),
    periodKey = periodKey,
    totalIncome = totalIncome,
    totalExpense = totalExpense,
    topCategory = topCategory,
    categoryBreakdown = runCatching {
        json.decodeFromString<Map<String, Double>>(categoryBreakdownJson)
    }.getOrDefault(emptyMap()),
    monthlyTrend = runCatching {
        json.decodeFromString<List<MonthlyTrendPoint>>(monthlyTrendJson)
    }.getOrDefault(emptyList()),
    topMerchants = runCatching {
        json.decodeFromString<List<MerchantSpend>>(topMerchantsJson)
    }.getOrDefault(emptyList())
)

fun AnalyticsSnapshot.toEntity() = AnalyticsSnapshotEntity(
    id = id,
    periodType = EntityPeriodType.valueOf(periodType.name),
    periodKey = periodKey,
    totalIncome = totalIncome,
    totalExpense = totalExpense,
    topCategory = topCategory,
    categoryBreakdownJson = json.encodeToString(categoryBreakdown),
    monthlyTrendJson = json.encodeToString(monthlyTrend),
    topMerchantsJson = json.encodeToString(topMerchants)
)