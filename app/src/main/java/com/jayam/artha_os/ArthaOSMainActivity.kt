package com.jayam.artha_os

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jayam.artha_os.core.navigation.BaseNavGraph
import com.jayam.artha_os.core.ui.theme.ArthaOSTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArthaOSMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArthaOSTheme(true) {
                BaseNavGraph()
//                AnalyticsScreenContent(
//                    state = AnalyticsUiState(summary = UiState.Success(sampleAnalytics)),
//                    onRetry = {}
//                )
//                DashboardScreen(
//                    state = HomeUiState(
//                        balance = UiState.Success(
//                            AccountBalance(
//                                totalBalance = 84320.50,
//                                accountCount = 2
//                            )
//                        ),
//                        budget = UiState.Success(
//                            BudgetSummary(
//                                id = "",
//                                spent = 12400.0,
//                                limit = 20000.0,
//                                category = "Food & dining"
//                            )
//                        ),
//                        recentTransactions = UiState.Error(
//                            message = "No internet connection",
//                            cachedData = listOf(
//                                TransactionItem(
//                                    "1",
//                                    "Zomato",
//                                    amount =  420.0,
//                                    isCredit = false,
//                                    category = "Food"
//                                )
//                            )
//                        )
//                    ),
//                    onRetryBalance = {},
//                    onRetryTransactions = {}
//                )
            }
        }
    }
}
