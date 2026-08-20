package com.jayam.artha_os.core.database.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jayam.artha_os.core.database.local.helper.TransactionSource
import com.jayam.artha_os.core.database.local.helper.TransactionType
import kotlin.uuid.Uuid

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["referenceId"], unique = true),
        Index(value = ["date"]),
        Index(value = ["type"])
    ]
)
data class TransactionEntity(
    @PrimaryKey
    val id: Uuid = Uuid.random(),   // client-generated, no autoGenerate
    val amount: Double,
    val type: TransactionType,
    val source: TransactionSource,
    val merchantName: String? = null,
    val category: String? = null,
    val description: String,
    val bankName: String? = null,
    val accountLast4: String? = null,
    val referenceId: String? = null,
    val rawSms: String? = null,
    val date: Long,
    val isRecurring: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)