package com.jayam.artha_os.feature.sms.parser

import com.jayam.artha_os.core.database.local.entities.SmsInfoEntity
import com.jayam.artha_os.feature.sms.parser.extractor.AccountExtractor
import com.jayam.artha_os.feature.sms.parser.extractor.AmountExtractor
import com.jayam.artha_os.feature.sms.parser.extractor.BalanceExtractor
import com.jayam.artha_os.feature.sms.parser.extractor.HashGenerator
import com.jayam.artha_os.feature.sms.parser.extractor.MerchantExtractor
import com.jayam.artha_os.feature.sms.parser.extractor.RefIdExtractor
import com.jayam.artha_os.feature.sms.parser.extractor.TxnTypeExtractor
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Instant

@Singleton
class TransactionParser @Inject constructor(
    private val amountExtractor: AmountExtractor,
    private val balanceExtractor: BalanceExtractor,
    private val merchantExtractor: MerchantExtractor,
    private val accountExtractor: AccountExtractor,
    private val refIdExtractor: RefIdExtractor,
    private val txnTypeExtractor: TxnTypeExtractor,
    private val hashGenerator: HashGenerator
) {

    fun parse(
        context: TransactionContext
    ): SmsInfoEntity? {

        val amount =
            amountExtractor.extract(context.body)
                ?: return null

        val txnType =
            txnTypeExtractor.extract(context.body)
                ?: return null

        return SmsInfoEntity(
            transactionType = txnType,
            amount = amount,
            dateTime = Instant.fromEpochMilliseconds(context.timestamp),
            senderId = context.sender,
            merchant = merchantExtractor.extract(context.body),
            accountLast4 = accountExtractor.extract(context.body),
            balance = balanceExtractor.extract(context.body),
            refId = refIdExtractor.extract(context.body),
            smsHash = hashGenerator.generate(
                context.sender,
                context.body
            ),
            rawSms = context.body
        )
    }
}
