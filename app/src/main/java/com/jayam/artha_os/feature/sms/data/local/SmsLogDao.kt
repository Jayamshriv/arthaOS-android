package com.jayam.artha_os.feature.sms.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jayam.artha_os.core.database.local.entities.ParseStatus
import com.jayam.artha_os.core.database.local.entities.SmsInfoEntity
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface SmsInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sms: SmsInfoEntity): Long

    @Query("SELECT * FROM sms_logs WHERE smsHash = :hash LIMIT 1")
    suspend fun getByHash(hash: String): SmsInfoEntity?

    @Query("SELECT * FROM sms_logs WHERE uuid = :id LIMIT 1")
    suspend fun getById(id: Uuid): SmsInfoEntity?

    @Query("SELECT * FROM sms_logs WHERE parseStatus = :status ORDER BY dateTime DESC")
    fun getByStatus(status: ParseStatus): Flow<List<SmsInfoEntity>>

    @Query("""
        UPDATE sms_logs 
        SET parseStatus = :status, linkedTransactionId = :transactionId 
        WHERE uuid = :id
    """)
    suspend fun markParsed(id: Uuid, status: ParseStatus, transactionId: Uuid)

    @Query("""
        UPDATE sms_logs 
        SET parseStatus = :status, failureReason = :reason 
        WHERE uuid = :id
    """)
    suspend fun markFailed(id: Uuid, status: ParseStatus, reason: String)

    @Query("SELECT * FROM sms_logs ORDER BY dateTime DESC")
    fun getAll(): Flow<List<SmsInfoEntity>>
}