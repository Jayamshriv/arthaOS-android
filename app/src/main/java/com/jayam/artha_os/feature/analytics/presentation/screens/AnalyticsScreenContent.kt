package com.jayam.artha_os.feature.analytics.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jayam.artha_os.core.ui.theme.ArthaOSTheme
import com.jayam.artha_os.core.ui.ui_utils.ErrorBanner
import com.jayam.artha_os.core.ui.ui_utils.LoadingRow
import com.jayam.artha_os.core.ui.ui_utils.StateSection
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.analytics.presentation.AnalyticsViewModel
import com.jayam.artha_os.feature.analytics.presentation.components.CategoryDistributionCard
import com.jayam.artha_os.feature.analytics.presentation.components.IncomeExpenseTrendCard
import com.jayam.artha_os.feature.analytics.presentation.components.MerchantRow

@Composable
fun AnalyticsScreenContent(
    viewModel: AnalyticsViewModel = hiltViewModel(),
    onRetry: () -> Unit
) {
    val state by viewModel.analyticsUiState.collectAsStateWithLifecycle()
    Scaffold(    containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    "Analytics",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }

            item {
                StateSection(state.summary, onRetry) { data, isRefreshing ->
                    CategoryDistributionCard(data.categoryBreakdown)
                }
            }

            item {
                StateSection(state.summary, onRetry) { data, isRefreshing ->
                    IncomeExpenseTrendCard(data.monthlyTrend)
                }
            }

            item {
                Text(
                    "Top merchants",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            }
            val merchantSummState = state.summary
            when (merchantSummState) {
                is UiState.Loading -> item { LoadingRow() }
                is UiState.Success -> items(merchantSummState.data.topMerchants) { MerchantRow(it) }
                is UiState.Error -> {
//                    val cached = merchantSummState.cachedData
//                    if (cached != null) {
//                        items(cached.topMerchants) { MerchantRow(it) }
                        item { ErrorBanner(merchantSummState.message, onRetry) }
//                    } else {
//                        item { ErrorBanner(merchantSummState.message, onRetry) }
//                    }
                }
            }
        }
    }
}
// ── Analytics ────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF121212, heightDp = 900)
@Composable
private fun AnalyticsScreenPreview() {
    ArthaOSTheme(true) {
        AnalyticsScreenContent(
            viewModel = hiltViewModel(),
            onRetry = {}
        )
    }
}