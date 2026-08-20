package com.jayam.artha_os.feature.profile.domain

data class UserProfile(
    val name: String,
    val email: String?,
    val monthlyIncome: String?,
    val currentStreakDays: Int,
    val notificationsEnabled: Boolean,
    val smsAutoCaptureEnabled: Boolean
)