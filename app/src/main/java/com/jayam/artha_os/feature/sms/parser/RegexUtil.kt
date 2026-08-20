package com.jayam.artha_os.feature.sms.parser

object RegexUtil {

    val amountRegex = Regex(
        """(?:₹|Rs\.?|INR)\s*([\d,]+(?:\.\d{1,2})?)""",
        RegexOption.IGNORE_CASE
    )

    val accountRegex = Regex(
        """(?:A/c|Acct|Account)[^\d]*(?:XX|X{2,})?(\d{4})""",
        RegexOption.IGNORE_CASE
    )

    val refIdRegex = Regex(
        """(?:Ref(?:erence)?|UTR|Txn|RRN)[\s:#-]*([A-Za-z0-9]+)""",
        RegexOption.IGNORE_CASE
    )

    val balanceRegex = Regex(
        """(?:Avl\.?\s*Bal|Available\s*Balance|Balance)[^\d₹]*(?:₹|Rs\.?|INR)?\s*([\d,]+(?:\.\d{1,2})?)""",
        RegexOption.IGNORE_CASE
    )
}