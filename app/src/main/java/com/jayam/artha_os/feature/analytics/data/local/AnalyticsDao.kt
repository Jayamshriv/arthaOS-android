package com.jayam.artha_os.feature.analytics.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayam.artha_os.core.database.local.entities.AnalyticsSnapshotEntity
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalyticsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(snapshot: AnalyticsSnapshotEntity): Long

    @Query("""
        SELECT * FROM analytics_snapshots 
        WHERE periodType = :periodType AND periodKey = :periodKey 
        LIMIT 1
    """)
    suspend fun getSnapshot(periodType: AnalyticsPeriodType, periodKey: String): AnalyticsSnapshotEntity?

    @Query("""
        SELECT * FROM analytics_snapshots 
        WHERE periodType = :periodType 
        ORDER BY periodKey DESC
    """)
    fun getSnapshotsByType(periodType: AnalyticsPeriodType): Flow<List<AnalyticsSnapshotEntity>>

    @Query("DELETE FROM analytics_snapshots WHERE periodType = :periodType AND periodKey = :periodKey")
    suspend fun invalidate(periodType: AnalyticsPeriodType, periodKey: String)
}