package com.jayam.artha_os.feature.sms.parser.extractor

import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BalanceExtractor @Inject constructor() {

    private val regex = Regex(
        """(?:Avl\.?\s*Bal|Available\s*Balance|Balance)[^\d₹]*(?:₹|Rs\.?|INR)?\s*([\d,]+(?:\.\d{1,2})?)""",
        RegexOption.IGNORE_CASE
    )

    fun extract(body: String): BigDecimal? {

        return regex.find(body)
            ?.groupValues
            ?.getOrNull(1)
            ?.replace(",", "")
            ?.toBigDecimalOrNull()
    }
}