package com.jayam.artha_os.feature.sms.domain

import com.jayam.artha_os.core.database.local.entities.ParseStatus
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface SmsRepository {
    suspend fun logSms(sms: SmsInfo): Long
    suspend fun insert(sms: SmsInfo): Long
    suspend fun getByHash(hash: String): SmsInfo?
    suspend fun getById(id: Uuid): SmsInfo?
    suspend fun markParsed(id: Uuid, transactionId: Uuid)
    suspend fun markFailed(id: Uuid, reason: String)
    suspend fun exists(id: String): Boolean
    fun getByStatus(status: ParseStatus): Flow<List<SmsInfo>>
    fun getAll(): Flow<List<SmsInfo>>
}