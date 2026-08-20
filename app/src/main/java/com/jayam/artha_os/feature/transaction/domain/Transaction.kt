package com.jayam.artha_os.feature.transaction.domain

import com.jayam.artha_os.core.database.local.helper.TransactionSource
import com.jayam.artha_os.core.database.local.helper.TransactionType
import kotlin.uuid.Uuid

data class Transaction(
    val id: Uuid = Uuid.random(),
    val amount: Double,
    val type: TransactionType,
    val source: TransactionSource,
    val merchantName: String?,
    val category: String?,
    val description: String,
    val bankName: String?,
    val accountLast4: String?,
    val referenceId: String?,
    val date: Long,
    val isRecurring: Boolean
)