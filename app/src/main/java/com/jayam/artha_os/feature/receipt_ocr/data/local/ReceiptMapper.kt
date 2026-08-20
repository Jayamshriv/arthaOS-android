package com.jayam.artha_os.feature.receipt_ocr.data.local

import com.jayam.artha_os.core.database.local.entities.ReceiptEntity
import com.jayam.artha_os.core.database.local.entities.ReceiptStatus as EntityStatus
import com.jayam.artha_os.feature.receipt_ocr.domain.Receipt

fun ReceiptEntity.toDomain() = Receipt(
    id = id, imagePath = imagePath, extractedMerchant = extractedMerchant,
    extractedAmount = extractedAmount, extractedDate = extractedDate,
    rawOcrText = rawOcrText, ocrConfidence = ocrConfidence,
    linkedTransactionId = linkedTransactionId, status = EntityStatus.valueOf(status.name)
)

fun Receipt.toEntity() = ReceiptEntity(
    id = id, imagePath = imagePath, extractedMerchant = extractedMerchant,
    extractedAmount = extractedAmount, extractedDate = extractedDate,
    rawOcrText = rawOcrText, ocrConfidence = ocrConfidence,
    linkedTransactionId = linkedTransactionId, status = EntityStatus.valueOf(status.name)
)