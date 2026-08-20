package com.jayam.artha_os.feature.sms.parser.extractor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MerchantExtractor @Inject constructor(){

    private val patterns = listOf(

        Regex("""to\s+([A-Za-z0-9 .&_-]+)""", RegexOption.IGNORE_CASE),

        Regex("""at\s+([A-Za-z0-9 .&_-]+)""", RegexOption.IGNORE_CASE),

        Regex("""towards\s+([A-Za-z0-9 .&_-]+)""", RegexOption.IGNORE_CASE),

        Regex("""from\s+([A-Za-z0-9 .&_-]+)""", RegexOption.IGNORE_CASE)
    )

    fun extract(body: String): String? {

        patterns.forEach {

            val value = it.find(body)
                ?.groupValues
                ?.getOrNull(1)

            if (!value.isNullOrBlank())
                return value.trim()
        }

        return null
    }
}
