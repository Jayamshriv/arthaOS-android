package com.jayam.artha_os.feature.sms.data

import com.jayam.artha_os.feature.sms.data.local.SmsInfoDao
import com.jayam.artha_os.feature.sms.data.local.toDomain
import com.jayam.artha_os.feature.sms.data.local.toEntity
import com.jayam.artha_os.feature.sms.domain.SmsInfo
import com.jayam.artha_os.feature.sms.domain.SmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.uuid.Uuid
import com.jayam.artha_os.core.database.local.entities.ParseStatus as EntityStatus

class SmsRepositoryImpl @Inject constructor(
    private val dao: SmsInfoDao
) : SmsRepository {

    override suspend fun logSms(sms: SmsInfo): Long = dao.insert(sms.toEntity())
    override suspend fun insert(sms: SmsInfo) = dao.insert(sms.toEntity())

    override suspend fun getByHash(hash: String): SmsInfo? = dao.getByHash(hash)?.toDomain()

    override suspend fun getById(id: Uuid): SmsInfo? = dao.getById(id)?.toDomain()

    override suspend fun markParsed(id: Uuid, transactionId: Uuid) =
        dao.markParsed(id, EntityStatus.PARSED, transactionId)

    override suspend fun markFailed(id: Uuid, reason: String) =
        dao.markFailed(id, EntityStatus.FAILED, reason)

    override suspend fun exists(id: String): Boolean  = dao.getByHash(id) !=null

    override fun getByStatus(status: EntityStatus): Flow<List<SmsInfo>> =
        dao.getByStatus(EntityStatus.valueOf(status.name)).map { list -> list.map { it.toDomain() } }

    override fun getAll(): Flow<List<SmsInfo>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }
}