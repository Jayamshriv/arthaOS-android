package com.jayam.artha_os.feature.sms

import com.jayam.artha_os.feature.sms.data.local.toDomain
import com.jayam.artha_os.feature.sms.domain.SmsRepository
import com.jayam.artha_os.feature.sms.parser.TransactionContext
import com.jayam.artha_os.feature.sms.parser.TransactionDetector
import com.jayam.artha_os.feature.sms.parser.TransactionParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmsProcessor @Inject constructor(
    private val repository: SmsRepository,
    private val detector: TransactionDetector,
    private val parser: TransactionParser
) {

    suspend fun process(
        sender: String,
        body: String,
        timestamp: Long
    ) {

        val context = TransactionContext(
            sender = sender,
            body = body,
            timestamp = timestamp
        )

        if (!detector.isTransaction(context))
            return

        val entity = parser.parse(context)
            ?: return

        if (repository.exists(entity.smsHash))
            return

        repository.insert(entity.toDomain() )
    }
}