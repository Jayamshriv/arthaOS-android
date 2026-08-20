package com.jayam.artha_os.feature.receipt_ocr.domain

import com.jayam.artha_os.core.database.local.entities.ReceiptStatus
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    suspend fun addReceipt(receipt: Receipt): Long
    suspend fun updateReceipt(receipt: Receipt)
    suspend fun deleteReceipt(receipt: Receipt)
    fun getAllReceipts(): Flow<List<Receipt>>
    fun getByStatus(status: ReceiptStatus): Flow<List<Receipt>>
    fun getUnlinkedReceipts(): Flow<List<Receipt>>
}