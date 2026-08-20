package com.jayam.artha_os.feature.ui_models

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class MonthlyTrendPoint(val monthLabel: String, val income: Double, val expense: Double)
