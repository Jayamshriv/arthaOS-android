package com.jayam.artha_os.feature.receipt_ocr.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jayam.artha_os.core.database.local.entities.ReceiptEntity
import com.jayam.artha_os.core.database.local.entities.ReceiptStatus
import kotlinx.coroutines.flow.Flow

// feature/receipt_ocr/data/local/ReceiptDao.kt
@Dao
interface ReceiptDao {
    @Insert
    suspend fun insert(receipt: ReceiptEntity): Long

    @Update
    suspend fun update(receipt: ReceiptEntity)

    @Delete
    suspend fun delete(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts ORDER BY createdAt DESC")
    fun getAllReceipts(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE status = :status ORDER BY createdAt DESC")
    fun getByStatus(status: ReceiptStatus): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE linkedTransactionId IS NULL AND status = :status")
    fun getUnlinkedReceipts(status: ReceiptStatus = ReceiptStatus.EXTRACTED): Flow<List<ReceiptEntity>>
}