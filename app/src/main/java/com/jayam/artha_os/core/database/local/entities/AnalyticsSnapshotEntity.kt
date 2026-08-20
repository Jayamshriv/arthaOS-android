package com.jayam.artha_os.core.database.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType


@Entity(
    tableName = "analytics_snapshots",
    indices = [Index(value = ["periodType", "periodKey"], unique = true)]
)
data class AnalyticsSnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val periodType: AnalyticsPeriodType,
    val periodKey: String,
    val totalIncome: Double,
    val totalExpense: Double,
    val topCategory: String? = null,
    val categoryBreakdownJson: String,
    val monthlyTrendJson: String,     // List<MonthlyTrendPoint> serialized
    val topMerchantsJson: String,     // List<MerchantSpend> serialized
    val computedAt: Long = System.currentTimeMillis()
)