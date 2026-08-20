package com.jayam.artha_os.feature.receipt_ocr.data

import com.jayam.artha_os.feature.receipt_ocr.data.local.ReceiptDao
import com.jayam.artha_os.feature.receipt_ocr.data.local.toDomain
import com.jayam.artha_os.feature.receipt_ocr.data.local.toEntity
import com.jayam.artha_os.feature.receipt_ocr.domain.Receipt
import com.jayam.artha_os.feature.receipt_ocr.domain.ReceiptRepository
import com.jayam.artha_os.core.database.local.entities.ReceiptStatus as EntityStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReceiptRepositoryImpl @Inject constructor(
    private val dao: ReceiptDao
) : ReceiptRepository {

    override suspend fun addReceipt(receipt: Receipt): Long = dao.insert(receipt.toEntity())

    override suspend fun updateReceipt(receipt: Receipt) = dao.update(receipt.toEntity())

    override suspend fun deleteReceipt(receipt: Receipt) = dao.delete(receipt.toEntity())

    override fun getAllReceipts(): Flow<List<Receipt>> =
        dao.getAllReceipts().map { list -> list.map { it.toDomain() } }

    override fun getByStatus(status: EntityStatus): Flow<List<Receipt>> =
        dao.getByStatus(EntityStatus.valueOf(status.name)).map { list -> list.map { it.toDomain() } }

    override fun getUnlinkedReceipts(): Flow<List<Receipt>> =
        dao.getUnlinkedReceipts().map { list -> list.map { it.toDomain() } }
}