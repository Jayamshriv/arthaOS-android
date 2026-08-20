package com.jayam.artha_os.feature.profile.data.local

import com.jayam.artha_os.core.database.local.entities.UserProfileEntity
import com.jayam.artha_os.feature.profile.domain.UserProfile

fun UserProfileEntity.toDomain() = UserProfile(
    name = name, email = email, monthlyIncome = monthlyIncome.toString(),
     currentStreakDays = currentStreakDays,
    notificationsEnabled = notificationsEnabled,
    smsAutoCaptureEnabled = smsAutoCaptureEnabled
)

fun UserProfile.toEntity() = UserProfileEntity(
    id = 1, name = name, email = email, monthlyIncome = monthlyIncome?.toDoubleOrNull(),
   currentStreakDays = currentStreakDays,
    notificationsEnabled = notificationsEnabled,
    smsAutoCaptureEnabled = smsAutoCaptureEnabled
)