package com.jayam.artha_os.feature.analytics.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jayam.artha_os.core.database.local.helper.TransactionType
import com.jayam.artha_os.core.ui.ui_utils.UiState
import com.jayam.artha_os.feature.analytics.domain.AnalyticsPeriodType
import com.jayam.artha_os.feature.analytics.domain.AnalyticsRepository
import com.jayam.artha_os.feature.analytics.domain.AnalyticsSnapshot
import com.jayam.artha_os.feature.analytics.domain.AnalyticsUiState
import com.jayam.artha_os.feature.transaction.domain.Transaction
import com.jayam.artha_os.feature.transaction.domain.repo.TransactionRepository
import com.jayam.artha_os.feature.ui_models.AnalyticsData
import com.jayam.artha_os.feature.ui_models.CategorySpend
import com.jayam.artha_os.feature.ui_models.MerchantSpend
import com.jayam.artha_os.feature.ui_models.MonthlyTrendPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AnalyticsUiState())
    val state = _state.asStateFlow()

    private val zone = ZoneId.systemDefault()

    init { load() }

    private fun load() {
        viewModelScope.launch {
            _state.value = AnalyticsUiState(UiState.Loading)
            try {
                val periodKey = YearMonth.now().toString()
                val cached = analyticsRepository.getSnapshot(AnalyticsPeriodType.MONTHLY, periodKey)

                val data = cached?.let {
                    AnalyticsData(
                        categoryBreakdown = toCategorySpend(it.categoryBreakdown.ifEmpty { hashMapOf(
                            "Gareeb" to 0.0
                        ) }),
                        monthlyTrend = it.monthlyTrend.ifEmpty { listOf<MonthlyTrendPoint>(
                            MonthlyTrendPoint(
                                monthLabel = periodKey,
                                income =0.0,
                                expense = 0.0
                            )
                        ) },
                        topMerchants = it.topMerchants
                    )
                } ?: computeAndCache(periodKey)

                _state.value = AnalyticsUiState(UiState.Success(data))
            } catch (e: Exception) {
                _state.value = AnalyticsUiState(UiState.Error(e.message ?: "Failed to load analytics"))
            }
        }
    }

    private suspend fun computeAndCache(periodKey: String): AnalyticsData {
        val (start, end) = monthRange(YearMonth.now())
        val currentMonthTxns = transactionRepository.getByDateRange(start, end).first()

        val breakdown = computeCategoryBreakdown(currentMonthTxns)
        val trend = computeMonthlyTrend(months = 6)
        val merchants = computeTopMerchants(currentMonthTxns, limit = 5)

        val income = currentMonthTxns.filter { it.type == TransactionType.CREDIT }.sumOf { it.amount }
        val expense = currentMonthTxns.filter { it.type == TransactionType.DEBIT }.sumOf { it.amount }

        analyticsRepository.saveSnapshot(
            AnalyticsSnapshot(
                periodType = AnalyticsPeriodType.MONTHLY,
                periodKey = periodKey,
                totalIncome = income,
                totalExpense = expense,
                topCategory = breakdown.maxByOrNull { it.value }?.key,
                categoryBreakdown = breakdown,
                monthlyTrend = trend,
                topMerchants = merchants
            )
        )

        return AnalyticsData(toCategorySpend(breakdown), trend, merchants)
    }

    private fun monthRange(ym: YearMonth): Pair<Long, Long> {
        val start = ym.atDay(1).atStartOfDay(zone).toInstant().toEpochMilli()
        val end = ym.atEndOfMonth().atTime(23, 59, 59).atZone(zone).toInstant().toEpochMilli()
        return start to end
    }

    private fun computeCategoryBreakdown(transactions: List<Transaction>): Map<String, Double> =
        transactions.filter { it.type == TransactionType.DEBIT }
            .groupBy { it.category ?: "Uncategorized" }
            .mapValues { (_, v) -> v.sumOf { it.amount } }

    private fun toCategorySpend(breakdown: Map<String, Double>): List<CategorySpend> {
        val total = breakdown.values.sum().takeIf { it > 0 } ?: 1.0
        return breakdown.entries
            .sortedByDescending { it.value }
            .map { (category, amount) ->
                CategorySpend(category, amount, (amount / total * 100).toFloat())
            }
    }

    private suspend fun computeMonthlyTrend(months: Int): List<MonthlyTrendPoint> {
        val now = YearMonth.now()
        return (months - 1 downTo 0).map { offset ->
            val ym = now.minusMonths(offset.toLong())
            val (start, end) = monthRange(ym)
            val txns = transactionRepository.getByDateRange(start, end).first()
            val income = txns.filter { it.type == TransactionType.CREDIT }.sumOf { it.amount }
            val expense = txns.filter { it.type == TransactionType.DEBIT }.sumOf { it.amount }
            MonthlyTrendPoint(
                monthLabel = ym.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                income = income,
                expense = expense
            )
        }
    }

    private fun computeTopMerchants(transactions: List<Transaction>, limit: Int): List<MerchantSpend> =
        transactions.filter { it.type == TransactionType.DEBIT && it.merchantName != null }
            .groupBy { it.merchantName!! }
            .map { (name, txns) ->
                MerchantSpend(
                    name = name,
                    totalSpent = txns.sumOf { it.amount },
                    transactionCount = txns.size
                )
            }
            .sortedByDescending { it.totalSpent }
            .take(limit)

    fun retry() = load()
}