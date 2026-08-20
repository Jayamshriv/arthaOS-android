package com.jayam.artha_os.feature.sms.parser.extractor

import com.jayam.artha_os.core.database.local.helper.TransactionType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxnTypeExtractor @Inject constructor() {

    fun extract(body: String): TransactionType? {

        val text = body.lowercase()

        return when {

            listOf("credited", "received").any(text::contains) ->
                TransactionType.CREDIT

            listOf("debited", "paid", "spent", "withdrawn")
                .any(text::contains) ->
                TransactionType.DEBIT

            else -> null
        }
    }
}