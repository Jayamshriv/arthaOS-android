package com.jayam.artha_os.feature.analytics.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jayam.artha_os.core.ui.theme.ArthaTheme
import com.jayam.artha_os.feature.ui_models.MonthlyTrendPoint
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.columnSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent

@Composable
fun IncomeExpenseTrendCard(trend: List<MonthlyTrendPoint>) {
    val incomeColor = ArthaTheme.colors.income
    val expenseColor = MaterialTheme.colorScheme.error

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(trend) {
        modelProducer.runTransaction {
            columnSeries {
                series(trend.map { it.income })
                series(trend.map { it.expense })
            }
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                "Income vs expense",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))

            val labels = trend.map { it.monthLabel }
            val bottomAxisValueFormatter = CartesianValueFormatter { _, value, _ ->
                labels.getOrElse(value.toInt()) { "" }
            }

            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberColumnCartesianLayer(
                        columnProvider = ColumnCartesianLayer.ColumnProvider.series(
                            rememberLineComponent(
                                fill = Fill(incomeColor),
                                thickness = 8.dp,
                                shape = RoundedCornerShape(
                                    topStart = 40.dp,
                                    topEnd = 40.dp
                                )
                            ),
                            rememberLineComponent(
                                fill = Fill(expenseColor),
                                thickness = 8.dp,
                                shape = RoundedCornerShape(
                                    topStart = 40.dp,
                                    topEnd = 40.dp
                                )
                            )
                        ),
                        columnCollectionSpacing = 20.dp,
                        mergeMode = { ColumnCartesianLayer.MergeMode.Grouped(0.dp) }
                    ),
                    startAxis = VerticalAxis.rememberStart(
                        label = rememberAxisLabelComponent(
                            style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        ),
                        guideline = null
                    ),
                    bottomAxis = HorizontalAxis.rememberBottom(
                        label = rememberAxisLabelComponent(
                            style = TextStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        ),
                        valueFormatter = bottomAxisValueFormatter,
                        guideline = null
                    )

                ),
                modelProducer = modelProducer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                LegendDot(incomeColor, "Income")
                LegendDot(expenseColor, "Expense")
            }
        }
    }
}