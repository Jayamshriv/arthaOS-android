package com.jayam.artha_os.feature.analytics.domain

import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.ui_models.AnalyticsData

data class AnalyticsUiState(
    val summary: UiState<AnalyticsData> = UiState.Loading
)