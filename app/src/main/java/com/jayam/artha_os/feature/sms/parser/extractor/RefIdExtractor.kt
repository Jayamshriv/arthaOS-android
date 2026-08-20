package com.jayam.artha_os.feature.sms.parser.extractor

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefIdExtractor @Inject constructor() {

    private val regex = Regex(
        """(?:Ref(?:erence)?|UTR|Txn|RRN)[\s:#-]*([A-Za-z0-9]+)""",
        RegexOption.IGNORE_CASE
    )

    fun extract(body: String): String? {

        return regex.find(body)
            ?.groupValues
            ?.getOrNull(1)
    }
}