package com.jayam.artha_os.core.database.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jayam.artha_os.core.database.local.helper.TransactionSource
import com.jayam.artha_os.core.database.local.helper.TransactionType

@Entity(
    tableName = "transactions",
    indices = [
        Index(value = ["referenceId"], unique = true),
        Index(value = ["date"]),
        Index(value = ["type"])
    ]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val amount: Double,
    val type: TransactionType,          // CREDIT / DEBIT
    val source: TransactionSource,      // SMS / MANUAL / RECEIPT_OCR

    val merchantName: String? = null,
    val category: String? = null,       // free-form for now; can normalize to CategoryEntity later
    val description: String,

    val bankName: String? = null,
    val accountLast4: String? = null,

    val referenceId: String? = null,    // bank txn ref — used to dedupe SMS ingestion
    val rawSms: String? = null,         // original SMS body, kept for debugging/re-parsing

    val date: Long,                     // epoch millis of the transaction itself
    val isRecurring: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
