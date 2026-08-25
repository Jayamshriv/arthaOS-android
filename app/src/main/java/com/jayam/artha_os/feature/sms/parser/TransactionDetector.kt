package com.jayam.artha_os.feature.sms.parser

import com.jayam.artha_os.feature.sms.parser.extractor.AmountExtractor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionDetector @Inject constructor(
    private val amountExtractor: AmountExtractor
) {

    fun isTransaction(context: TransactionContext): Boolean {

        val body = context.body.lowercase()

        val hasAmount =
            amountExtractor.extract(context.body) != null

        val hasKeyword = listOf(
            "debited",
            "credited",
            "received",
            "spent",
            "withdrawn",
            "upi",
            "sent",
            "credit alert",
            "debit",
            "transaction",
            "txn"
        ).any(body::contains)

        return hasAmount && hasKeyword
    }
}