package com.jayam.artha_os.core.database.local.helper

data class CrDbInfo(
    val from: String? = null,
    val to: String? = null,
    val accountLast4: String? = null,
    val merchant: String? = null,
    val paymentMode: PaymentMode = PaymentMode.UNKNOWN,
    val bankName: String? = null
)