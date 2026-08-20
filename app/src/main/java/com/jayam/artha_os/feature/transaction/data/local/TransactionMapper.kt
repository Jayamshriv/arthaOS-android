package com.jayam.artha_os.feature.transaction.data.local

import com.jayam.artha_os.core.database.local.entities.TransactionEntity
import com.jayam.artha_os.feature.transaction.domain.Transaction
import com.jayam.artha_os.core.database.local.helper.TransactionSource as EntitySource
import com.jayam.artha_os.core.database.local.helper.TransactionType as EntityType


fun TransactionEntity.toDomain() = Transaction(
    id = id,
    amount = amount,
    type = EntityType.valueOf(type.name),
    source = EntitySource.valueOf(source.name),
    merchantName = merchantName,
    category = category,
    description = description,
    bankName = bankName,
    accountLast4 = accountLast4,
    referenceId = referenceId,
    date = date,
    isRecurring = isRecurring
)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    amount = amount,
    type = EntityType.valueOf(type.name),
    source = EntitySource.valueOf(source.name),
    merchantName = merchantName,
    category = category,
    description = description,
    bankName = bankName,
    accountLast4 = accountLast4,
    referenceId = referenceId,
    date = date,
    isRecurring = isRecurring
)