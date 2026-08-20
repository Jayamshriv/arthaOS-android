package com.jayam.artha_os.feature.profile.data

import com.jayam.artha_os.feature.profile.data.local.ProfileDao
import com.jayam.artha_os.feature.profile.data.local.toDomain
import com.jayam.artha_os.feature.profile.data.local.toEntity
import com.jayam.artha_os.feature.profile.domain.ProfileRepository
import com.jayam.artha_os.feature.profile.domain.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val dao: ProfileDao
) : ProfileRepository {

    override suspend fun saveProfile(profile: UserProfile) = dao.upsert(profile.toEntity())

    override suspend fun setSmsAutoCapture(enabled: Boolean) = dao.setSmsAutoCapture(enabled)

    override fun getProfile(): Flow<UserProfile?> =
        dao.getProfile().map { it?.toDomain() }
}