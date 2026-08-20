package com.jayam.artha_os.feature.ui_models

data class TransactionItem(
    val id: String,
    val merchant: String ="",
    val category: String ="",
    val amount: Double =0.0,
    val isCredit: Boolean = false,
    val dateLabel: String = "",   // "Today", "Yesterday", "12 July"
    val timestamp: Long = 0L,
)