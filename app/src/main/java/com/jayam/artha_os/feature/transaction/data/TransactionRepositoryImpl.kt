package com.jayam.artha_os.feature.transaction.data

import com.jayam.artha_os.feature.transaction.data.local.TransactionDao
import com.jayam.artha_os.feature.transaction.data.local.toDomain
import com.jayam.artha_os.feature.transaction.data.local.toEntity
import com.jayam.artha_os.feature.transaction.domain.Transaction
import com.jayam.artha_os.feature.transaction.domain.repo.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.uuid.Uuid
import com.jayam.artha_os.core.database.local.helper.TransactionType as EntityType

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override suspend fun addTransaction(transaction: Transaction): Uuid {
        dao.insert(transaction.toEntity())
        return transaction.id
    }

    override suspend fun addTransactions(transactions: List<Transaction>): List<Uuid> {
        dao.insertAll(transactions.map { it.toEntity() })
        return transactions.map { it.id }
    }

    override suspend fun getById(id: Uuid): Transaction? = dao.getById(id)?.toDomain()
    override suspend fun updateTransaction(transaction: Transaction) =
        dao.update(transaction.toEntity())

    override suspend fun deleteTransaction(transaction: Transaction) =
        dao.delete(transaction.toEntity())


    override suspend fun existsByReferenceId(referenceId: String): Boolean =
        dao.getByReferenceId(referenceId) != null

    override fun getAllTransactions(): Flow<List<Transaction>> =
        dao.getAllTransactions().map { list -> list.map { it.toDomain() } }

    override fun getByDateRange(start: Long, end: Long): Flow<List<Transaction>> =
        dao.getTransactionsByDateRange(start, end).map { list -> list.map { it.toDomain() } }

    override fun getByType(type: EntityType): Flow<List<Transaction>> =
        dao.getTransactionsByType(EntityType.valueOf(type.name)).map { list -> list.map { it.toDomain() } }

    override fun getByCategory(category: String): Flow<List<Transaction>> =
        dao.getTransactionsByCategory(category).map { list -> list.map { it.toDomain() } }

    override fun search(query: String): Flow<List<Transaction>> =
        dao.search(query).map { list -> list.map { it.toDomain() } }

    override fun getTotalByType(type: EntityType, start: Long, end: Long): Flow<Double?> =
        dao.getTotalByType(EntityType.valueOf(type.name), start, end)
}