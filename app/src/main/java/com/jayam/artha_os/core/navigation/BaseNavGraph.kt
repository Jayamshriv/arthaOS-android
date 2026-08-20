package com.jayam.artha_os.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.analytics.presentation.screens.AnalyticsScreenContent
import com.jayam.artha_os.feature.budget.presentation.screens.BudgetsScreenContent
import com.jayam.artha_os.feature.dashboard.presentation.screen.DashboardScreen
import com.jayam.artha_os.feature.dashboard.presentation.screen.HomeUiState
import com.jayam.artha_os.feature.profile.presentation.ProfileScreenContent
import com.jayam.artha_os.feature.receipt_ocr.screens.ReceiptOcrScreen
import com.jayam.artha_os.feature.transaction.presentation.sampleBudgets
import com.jayam.artha_os.feature.transaction.presentation.sampleTransactions
import com.jayam.artha_os.feature.transaction.presentation.screens.TransactionsScreenContent
import com.jayam.artha_os.feature.transaction.presentation.screens.TransactionsUiState
import com.jayam.artha_os.feature.ui_models.AccountBalance
import com.jayam.artha_os.feature.ui_models.BudgetSummary

/**
 * ---------------------------------------------------------------------
 * BOTTOM NAV CONFIG
 * ---------------------------------------------------------------------
 * Only these four are tabs. ProfileScreen and ReceiptOCRScreen are
 * reached by navigating to them directly (profile icon / FAB or
 * wherever you trigger OCR from) — they don't get a slot here.
 * ---------------------------------------------------------------------
 */
private data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: NavRoutes
)

private val bottomNavItems = listOf(
    BottomNavItem("Dashboard", Icons.Filled.Home, NavRoutes.DashboardScreen),
    BottomNavItem("Transactions", Icons.Filled.List, NavRoutes.TransactionsScreen),
    BottomNavItem("Budget", Icons.Filled.Wallet, NavRoutes.BudgetScreen),
    BottomNavItem("Analytics", Icons.Filled.PieChart, NavRoutes.AnalyticsScreen),
)

/** Screens that get NO chrome at all (full-bleed) — add more here as needed. */
private fun isChromeless(route: androidx.navigation.NavDestination?): Boolean =
    route?.hasRoute<NavRoutes.ReceiptOCRScreen>() == true

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val isProfile = currentDestination?.hasRoute<NavRoutes.ProfileScreen>() == true
    val isBottomNavRoute = bottomNavItems.any { item ->
        when (item.route) {
            NavRoutes.DashboardScreen -> currentDestination?.hasRoute<NavRoutes.DashboardScreen>() == true
            NavRoutes.TransactionsScreen -> currentDestination?.hasRoute<NavRoutes.TransactionsScreen>() == true
            NavRoutes.BudgetScreen -> currentDestination?.hasRoute<NavRoutes.BudgetScreen>() == true
            NavRoutes.AnalyticsScreen -> currentDestination?.hasRoute<NavRoutes.AnalyticsScreen>() == true
            else -> false
        }
    }
    val showChrome = !isChromeless(currentDestination)
    val showBottomBar = showChrome && isBottomNavRoute
    val currentTitle = when {
        currentDestination?.hasRoute<NavRoutes.DashboardScreen>() == true -> "Dashboard"
        currentDestination?.hasRoute<NavRoutes.TransactionsScreen>() == true -> "Transactions"
        currentDestination?.hasRoute<NavRoutes.BudgetScreen>() == true -> "Budget"
        currentDestination?.hasRoute<NavRoutes.AnalyticsScreen>() == true -> "Analytics"
        currentDestination?.hasRoute<NavRoutes.ProfileScreen>() == true -> "Profile"
        else -> ""
    }

    Scaffold(
        topBar = {
            if (showChrome) {
                TopAppBar(
                    title = { Text(currentTitle) },
                    navigationIcon = {
                        if (isProfile) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        if (!isProfile) {
                            IconButton(onClick = { navController.navigate(NavRoutes.ProfileScreen) }) {
                                Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        val selected = when (item.route) {
                            NavRoutes.DashboardScreen -> currentDestination?.hasRoute<NavRoutes.DashboardScreen>() == true
                            NavRoutes.TransactionsScreen -> currentDestination?.hasRoute<NavRoutes.TransactionsScreen>() == true
                            NavRoutes.BudgetScreen -> currentDestination?.hasRoute<NavRoutes.BudgetScreen>() == true
                            NavRoutes.AnalyticsScreen -> currentDestination?.hasRoute<NavRoutes.AnalyticsScreen>() == true
                            else -> false
                        }
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val contentModifier = if (showChrome) Modifier.padding(innerPadding) else Modifier

        NavHost(
            navController = navController,
            startDestination = NavRoutes.DashboardScreen, // was ProfileScreen — flip back if that was intentional
            modifier = contentModifier
        ) {
            composable<NavRoutes.DashboardScreen> {
                DashboardScreen(
                    state = HomeUiState(
                        balance = UiState.Success(AccountBalance(totalBalance = 84320.50, accountCount = 2)),
                        budget = UiState.Success(
                            BudgetSummary(
                                spent = 19400.0,
                                limit = 20000.0,
                                category = "Food & dining"
                            )
                        ),
                        recentTransactions = UiState.Error(
                            message = "No internet connection",
//                        cachedData = listOf(
//                            TransactionItem("1", "Zomato", amount = 420.0, isCredit = false, category = "Food")
//                        )
                        )
                    ),
                    onRetryBalance = {},
                    onRetryTransactions = {
                        navController.navigate(NavRoutes.AnalyticsScreen)
                    }
                )
            }

            composable<NavRoutes.AnalyticsScreen> {
                AnalyticsScreenContent(
//                state = AnalyticsUiState(summary = UiState.Success(sampleAnalytics)),
//                state = AnalyticsUiState(summary = UiState.Error("sampleAnalytics")),
                    onRetry = {
                        navController.navigate(NavRoutes.TransactionsScreen)
                    })
            }

            composable<NavRoutes.TransactionsScreen> { backStackEntry ->
                TransactionsScreenContent(
                    state = TransactionsUiState(
                        transactions = UiState.Success(sampleTransactions),
                        selectedFilter = "All"
                    ),
//                state = TransactionsUiState(transactions = UiState.Error("emptyList()")),
                    selectedFilter = "Food",
                    onFilterSelected = {
                        navController.navigate(NavRoutes.BudgetScreen)
                    },
                    onSearchQueryChange = {

                    },
                    onRetry = {
                        navController.navigate(NavRoutes.BudgetScreen)
                    }
                )
            }

            composable<NavRoutes.BudgetScreen> { backStackEntry ->
                BudgetsScreenContent(
//                state = UiState.Success(data = emptyList()),
                    state = UiState.Success(data = sampleBudgets),
                    onAddBudget = {},
                    onEditBudget = {},
                    onRetry = {
                        navController.navigate(NavRoutes.DashboardScreen)
                    }
                )
            }

            composable<NavRoutes.ReceiptOCRScreen> { backStackEntry ->
                // Automatically extracts parameters safely
//            val arguments = backStackEntry.toRoute<NavRoutes.Details>()

                ReceiptOcrScreen()
            }

            composable<NavRoutes.ProfileScreen> { backStackEntry ->
                // Automatically extracts parameters safely
//            val arguments = backStackEntry.toRoute<NavRoutes.Details>()

                ProfileScreenContent()
            }
        }
    }
}