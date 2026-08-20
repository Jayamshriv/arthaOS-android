package com.jayam.artha_os.core.database.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imagePath: String,
    val extractedMerchant: String? = null,
    val extractedAmount: Double? = null,
    val extractedDate: Long? = null,
    val rawOcrText: String,
    val ocrConfidence: Float? = null,
    val linkedTransactionId: Long? = null,
    val status: ReceiptStatus = ReceiptStatus.PROCESSING,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ReceiptStatus { PROCESSING, EXTRACTED, LINKED, FAILED }