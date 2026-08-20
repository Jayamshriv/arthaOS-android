package com.jayam.artha_os.feature.analytics.data.repository

import com.jayam.artha_os.feature.analytics.data.local.AnalyticsDao
import com.jayam.artha_os.feature.analytics.data.local.toDomain
import com.jayam.artha_os.feature.analytics.data.local.toEntity
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType
import com.jayam.artha_os.feature.analytics.domain.AnalyticsRepository
import com.jayam.artha_os.feature.analytics.domain.AnalyticsSnapshot
import jakarta.inject.Inject

class AnalyticsRepositoryImpl @Inject constructor(
   private val dao: AnalyticsDao
)  : AnalyticsRepository {
    override suspend fun saveSnapshot(snapshot: AnalyticsSnapshot) {
        dao.upsert(snapshot.toEntity())
    }

    override suspend fun getSnapshot(
        periodType: AnalyticsPeriodType,
        periodKey: String
    ): AnalyticsSnapshot? =
            dao.getSnapshot(
                periodType = periodType,
                periodKey = periodKey
            )?.toDomain()

}