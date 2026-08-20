package com.jayam.artha_os.feature.sms.parser

data class TransactionContext(
    val sender: String,
    val body: String,
    val timestamp: Long
)