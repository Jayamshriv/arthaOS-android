package com.jayam.artha_os.feature.analytics.domain

interface AnalyticsRepository {
    suspend fun saveSnapshot(snapshot: AnalyticsSnapshot)
    suspend fun getSnapshot(periodType: AnalyticsPeriodType, periodKey: String): AnalyticsSnapshot?
}