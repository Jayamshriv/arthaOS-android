package com.jayam.artha_os.feature.sms.parser.extractor

import javax.inject.Inject
import javax.inject.Singleton
import java.math.BigDecimal

@Singleton
class AmountExtractor @Inject constructor() {

    private val regex = Regex(
        """(?:₹|Rs\.?|INR)\s*([\d,]+(?:\.\d{1,2})?)""",
        RegexOption.IGNORE_CASE
    )

    fun extract(body: String): BigDecimal? {

        return regex.find(body)
            ?.groupValues
            ?.get(1)
            ?.replace(",", "")
            ?.toBigDecimalOrNull()
    }
}