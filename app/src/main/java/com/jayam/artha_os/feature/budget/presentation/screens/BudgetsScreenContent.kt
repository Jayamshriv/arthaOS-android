package com.jayam.artha_os.feature.budget.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jayam.artha_os.core.ui.theme.ArthaOSTheme
import com.jayam.artha_os.core.ui.theme.ArthaTheme
import com.jayam.artha_os.core.ui.ui_utils.StateSection
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.budget.presentation.components.BudgetList
import com.jayam.artha_os.feature.budget.presentation.components.EmptyState
import com.jayam.artha_os.feature.transaction.presentation.sampleBudgets
import com.jayam.artha_os.feature.ui_models.BudgetSummary

@Composable
fun BudgetsScreenContent(
    state: UiState<List<BudgetSummary>>,
    onAddBudget: () -> Unit,
    onEditBudget: (BudgetSummary) -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBudget, containerColor = MaterialTheme.colorScheme.primary, contentColor = Color.Black) {
                Icon(Icons.Default.Add, contentDescription = "Add budget")
            }
        }
    ) { padding ->
//        when (state) {
//            is UiState.Loading -> {
//                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
//                }
//            }
//            is UiState.Error -> {
//                val cached = state.cachedData
//                if (cached != null) {
//                    BudgetList(cached, padding, onEditBudget)
//                } else {
//                    Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
//                        ErrorBanner(state.message, onRetry)
//                    }
//                }
//            }
//            is UiState.Success -> {
        StateSection(state,onRetry) { data, isRefreshing ->
            if (data.isEmpty()) {
                EmptyState(
                    padding = padding,
                    icon = Icons.Default.PieChart,
                    title = "No budgets set",
                    description = "Set a monthly limit per category to start tracking",
                    buttonText = "Add budget",
                    onButtonClick = onAddBudget
                )
            } else {
                BudgetList(data, padding, onEditBudget)
            }
        }
//            }
//        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun BudgetsScreenPreview() {
    ArthaOSTheme(true) {
        BudgetsScreenContent(
            state = UiState.Success(data = sampleBudgets),
            onAddBudget = {},
            onEditBudget = {},
            onRetry = {}
        )
    }
}

@Preview(name = "Budgets - Empty", showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun BudgetsScreenEmptyPreview() {
    ArthaOSTheme(true) {
        BudgetsScreenContent(
            state = UiState.Success(emptyList()),
            onAddBudget = {},
            onEditBudget = {},
            onRetry = {}
        )
    }
}



@Composable
fun categoryColor(category: String): Color {
    val colors = ArthaTheme.colors

    return when (category) {
        "Food" -> colors.catFood
        "Travel" -> colors.catTravel
        "Shopping" -> colors.catShopping
        "Bills" -> colors.catBills
        "Entertainment" -> colors.catEntertain
        "Health" -> colors.catHealth
        "Education" -> colors.catEducation
        "Salary" -> colors.catSalary
        "Investments" -> colors.catInvestments
        else -> colors.catOther
    }
}

