package com.jayam.artha_os.feature.ui_models

import kotlinx.serialization.Serializable


@Serializable
data class MerchantSpend(val name: String, val totalSpent: Double, val transactionCount: Int)