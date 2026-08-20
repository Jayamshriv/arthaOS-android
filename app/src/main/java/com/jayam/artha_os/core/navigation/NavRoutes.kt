package com.jayam.artha_os.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavRoutes {
    @Serializable
    data object DashboardScreen : NavRoutes
    @Serializable
    data object AnalyticsScreen : NavRoutes

    @Serializable
    data object BudgetScreen : NavRoutes
    @Serializable
    data object ReceiptOCRScreen : NavRoutes

    @Serializable
    data object TransactionsScreen : NavRoutes
    @Serializable
    data object ProfileScreen : NavRoutes
}