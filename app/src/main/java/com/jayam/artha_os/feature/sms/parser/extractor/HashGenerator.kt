package com.jayam.artha_os.feature.sms.parser.extractor

import java.security.MessageDigest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HashGenerator @Inject constructor() {

    fun generate(
        sender: String,
        body: String
    ): String {

        val digest = MessageDigest.getInstance("SHA-256")

        val hash = digest.digest(
            "$sender|$body".toByteArray()
        )

        return hash.joinToString("") {
            "%02x".format(it)
        }
    }
}
