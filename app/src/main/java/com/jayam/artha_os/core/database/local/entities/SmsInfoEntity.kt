@file:OptIn(ExperimentalUuidApi::class)

package com.jayam.artha_os.core.database.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jayam.artha_os.core.database.local.helper.TransactionSource
import com.jayam.artha_os.core.database.local.helper.TransactionType
import java.math.BigDecimal
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "sms_logs",
    indices = [Index(value = ["smsHash"], unique = true)]
)
data class SmsInfoEntity(
    @PrimaryKey
    val uuid: Uuid = Uuid.random(),
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val dateTime: Instant,
    val senderId: String,
    val merchant: String?,
    val accountLast4: String?,
    val balance: BigDecimal?,
    val refId: String?,
    val parseStatus: ParseStatus = ParseStatus.PENDING,
    val smsHash: String,
    val rawSms: String,
    val failureReason: String? = null,
    val parsedVersion: Int = 1,
    val transactionSource: TransactionSource? = null,
    val linkedTransactionId: Uuid? = null
)

enum class ParseStatus { PENDING, PARSED, IGNORED, FAILED }