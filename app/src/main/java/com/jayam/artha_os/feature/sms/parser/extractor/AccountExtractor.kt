package com.jayam.artha_os.feature.sms.parser.extractor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountExtractor @Inject constructor(){

    private val regex = Regex(
        """(?:A/c|Acct|Account)[^\d]*(?:XX|X{2,})?(\d{4})""",
        RegexOption.IGNORE_CASE
    )

    fun extract(body: String): String? {

        return regex.find(body)
            ?.groupValues
            ?.getOrNull(1)
    }
}