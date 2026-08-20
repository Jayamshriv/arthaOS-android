package com.jayam.artha_os.feature.profile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayam.artha_os.core.database.local.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: UserProfileEntity)

    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getProfile(): Flow<UserProfileEntity?>

    @Query("UPDATE user_profile SET smsAutoCaptureEnabled = :enabled WHERE id = 1")
    suspend fun setSmsAutoCapture(enabled: Boolean)
}