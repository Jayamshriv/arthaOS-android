package com.jayam.artha_os.feature.receipt_ocr.domain

import com.jayam.artha_os.core.database.local.entities.ReceiptStatus

data class Receipt(
    val id: Long = 0,
    val imagePath: String,
    val extractedMerchant: String?,
    val extractedAmount: Double?,
    val extractedDate: Long?,
    val rawOcrText: String,
    val ocrConfidence: Float?,
    val linkedTransactionId: Long?,
    val status: ReceiptStatus
)

