package com.jayam.artha_os.feature.sms.data.local

import com.jayam.artha_os.core.database.local.entities.SmsInfoEntity
import com.jayam.artha_os.core.database.local.entities.ParseStatus as EntityParseStatus
import com.jayam.artha_os.feature.sms.domain.SmsInfo
import com.jayam.artha_os.core.database.local.helper .TransactionSource as EntitySource
import com.jayam.artha_os.core.database.local.helper.TransactionType as EntityType

fun SmsInfoEntity.toDomain() = SmsInfo(
    uuid = uuid,
    transactionType = EntityType.valueOf(transactionType.name),
    amount = amount,
    dateTime = dateTime,
    senderId = senderId,
    merchant = merchant,
    accountLast4 = accountLast4,
    balance = balance,
    refId = refId,
    parseStatus = EntityParseStatus.valueOf(parseStatus.name),
    smsHash = smsHash,
    rawSms = rawSms,
    failureReason = failureReason,
    parsedVersion = parsedVersion,
    transactionSource = transactionSource?.let { EntitySource.valueOf(it.name) },
    linkedTransactionId = linkedTransactionId
)

fun SmsInfo.toEntity() = SmsInfoEntity(
    uuid = uuid,
    transactionType = EntityType.valueOf(transactionType.name),
    amount = amount,
    dateTime = dateTime,
    senderId = senderId,
    merchant = merchant,
    accountLast4 = accountLast4,
    balance = balance,
    refId = refId,
    parseStatus = EntityParseStatus.valueOf(parseStatus.name),
    smsHash = smsHash,
    rawSms = rawSms,
    failureReason = failureReason,
    parsedVersion = parsedVersion,
    transactionSource = transactionSource?.let { EntitySource.valueOf(it.name) },
    linkedTransactionId = linkedTransactionId
)