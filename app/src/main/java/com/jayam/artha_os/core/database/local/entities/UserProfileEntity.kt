package com.jayam.artha_os.core.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val name: String,
    val email: String? = null,
    val monthlyIncome: Double? = null,
    val currency: String = "INR",
    val currentStreakDays: Int = 0,
    val notificationsEnabled: Boolean = true,
    val smsAutoCaptureEnabled: Boolean = true,
    val updatedAt: Long = System.currentTimeMillis()
)