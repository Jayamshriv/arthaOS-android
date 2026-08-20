package com.jayam.artha_os.feature.profile.domain

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun saveProfile(profile: UserProfile)
    suspend fun setSmsAutoCapture(enabled: Boolean)
    fun getProfile(): Flow<UserProfile?>
}