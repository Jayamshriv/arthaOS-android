package com.jayam.artha_os.feature.analytics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.core.utils.todayKey
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType
import com.jayam.artha_os.feature.analytics.domain.AnalyticsRepository
import com.jayam.artha_os.feature.analytics.domain.AnalyticsSnapshot
import com.jayam.artha_os.feature.analytics.domain.AnalyticsUiState
import com.jayam.artha_os.feature.ui_models.AnalyticsData
import com.jayam.artha_os.feature.ui_models.CategorySpend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: AnalyticsRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            getAnalyticsUiState()
        }
    }
    private val _analyticsUiState = MutableStateFlow(AnalyticsUiState())
    val analyticsUiState = _analyticsUiState.asStateFlow()

    private fun saveSnapshot(snapshot: AnalyticsSnapshot) {
        viewModelScope.launch {
            repository.saveSnapshot(snapshot)
        }
    }

    private suspend fun getSnapshot(
        periodType: AnalyticsPeriodType,
        periodKey: String
    ): AnalyticsSnapshot? {
        return repository.getSnapshot(periodType, periodKey)
    }


    private suspend fun getAnalyticsUiState(
        periodType: AnalyticsPeriodType = AnalyticsPeriodType.DAILY,
        periodKey: String = todayKey()
    ) {
        val data = getSnapshot(
            periodType,
            periodKey
        )

        if(data==null){
            _analyticsUiState.value = AnalyticsUiState(summary = UiState.Error("Kuch to hua hai tagda"))
            return
        }
        _analyticsUiState.value = AnalyticsUiState(summary = UiState.Success(
            data = AnalyticsData(
                categoryBreakdown = data.categoryBreakdown.mapNotNull {
                    CategorySpend(
                        category = it.key,
                        amount = it.value,
                        percentage = (it.value/data.totalExpense).toFloat()
                    )
                },
                monthlyTrend = emptyList(),
                topMerchants = emptyList()
            )
        ))
    }

}