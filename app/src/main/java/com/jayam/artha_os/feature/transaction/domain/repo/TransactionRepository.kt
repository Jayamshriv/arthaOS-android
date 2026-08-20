package com.jayam.artha_os.feature.transaction.domain.repo

import com.jayam.artha_os.core.database.local.helper.TransactionType
import com.jayam.artha_os.feature.transaction.domain.Transaction


import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction): Uuid
    suspend fun addTransactions(transactions: List<Transaction>): List<Uuid>
    suspend fun updateTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun getById(id: Uuid): Transaction?
    suspend fun existsByReferenceId(referenceId: String): Boolean
    fun getAllTransactions(): Flow<List<Transaction>>
    fun getByDateRange(start: Long, end: Long): Flow<List<Transaction>>
    fun getByType(type: TransactionType): Flow<List<Transaction>>
    fun getByCategory(category: String): Flow<List<Transaction>>
    fun search(query: String): Flow<List<Transaction>>
    fun getTotalByType(type: TransactionType, start: Long, end: Long): Flow<Double?>
}