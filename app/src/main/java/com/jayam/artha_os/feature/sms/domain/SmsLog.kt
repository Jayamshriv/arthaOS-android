package com.jayam.artha_os.feature.sms.domain

import com.jayam.artha_os.core.database.local.entities.ParseStatus
import com.jayam.artha_os.core.database.local.helper.TransactionSource
import com.jayam.artha_os.core.database.local.helper.TransactionType
import java.math.BigDecimal
import kotlin.time.Instant
import kotlin.uuid.Uuid

data class SmsInfo(
    val uuid: Uuid = Uuid.random(),
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val dateTime: Instant,
    val senderId: String,
    val merchant: String?,
    val accountLast4: String?,
    val balance: BigDecimal?,
    val refId: String?,
    val parseStatus: ParseStatus,
    val smsHash: String,
    val rawSms: String,
    val failureReason: String?,
    val parsedVersion: Int,
    val transactionSource: TransactionSource?,
    val linkedTransactionId: Uuid?
)
