package com.smithjilks.mpesaexpensetracker.feature.statistics

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smithjilks.mpesaexpensetracker.core.R
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.AppButton
import com.smithjilks.mpesaexpensetracker.core.model.CategorySummary
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.widgets.AppButtonToggleGroup
import com.smithjilks.mpesaexpensetracker.core.widgets.BottomNavigation
import com.smithjilks.mpesaexpensetracker.feature.dashboard.ColumnAmountAndDate
import com.smithjilks.mpesaexpensetracker.feature.dashboard.ColumnNoteAndCategory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StatisticsScreen(
    navController: NavController,
    statisticsViewModel: StatisticsViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {
        MainStatisticsContent(
            statisticsViewModel = statisticsViewModel,
            navController = navController
        )
    }

}

@Composable
fun MainStatisticsContent(
    modifier: Modifier = Modifier,
    statisticsViewModel: StatisticsViewModel,
    navController: NavController
) {

    Column {

        AppButtonToggleGroup(buttons = listOf(
            AppButton(
                title = "Line Chart Button",
                isIconButton = true,
                iconResource = R.drawable.chart_icon
            ),
            AppButton(
                title = "Bar Chart Button",
                isIconButton = true,
                iconResource = R.drawable.bar_chart_icon,
                callback = {

                }
            ),
            AppButton(
                title = "Pie Chart Button",
                isIconButton = true,
                iconResource = R.drawable.pie_chart_icon
            )

        )
        )

        ExpenseToggleRow(statisticsViewModel = statisticsViewModel)
        TransactionsCategories(statisticsViewModel = statisticsViewModel)

    }
}


@Composable
fun TransactionsCategories(
    modifier: Modifier = Modifier,
    statisticsViewModel: StatisticsViewModel,
) {
    val categories =
        statisticsViewModel.summarizedCategoriesList.collectAsState(initial = emptyList()).value

    val sortByDescending = statisticsViewModel.sortByDescending.collectAsState()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Text(
                modifier = Modifier.padding(4.dp),
                text = "Categories",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedIconToggleButton(
                border = BorderStroke(
                    width = 0.25.dp,
                    color = Color.Transparent
                ),
                shape = RoundedCornerShape(4.dp),
                colors = IconButtonDefaults.filledTonalIconToggleButtonColors(
                    containerColor = Color.Transparent,
                    checkedContainerColor = Color.Transparent
                ),
                checked = false,
                onCheckedChange = {
                    statisticsViewModel.updateSortingByDescending(!sortByDescending.value)
                }
            ) {
                if (sortByDescending.value) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.sort_descending_icon),
                        contentDescription = "Sort Ascending icon"
                    )
                } else {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.sort_ascending_icon),
                        contentDescription = "Sort Descending icon "
                    )
                }
            }
        }

    }

    LazyColumn {
        items(categories) {
            CategorySummaryRow(categorySummary = it, modifier)
        }
    }


}

@Composable
fun CategorySummaryRow(categorySummary: CategorySummary, modifier: Modifier = Modifier) {

    val record = Record(
        id = 0,
        transactionRef = "",
        category = categorySummary.category,
        amount = categorySummary.total,
        transactionCost = 0,
        note = "",
        timestamp = null,
        account = "",
        payee = "",
        recordType = categorySummary.categoryType,
        storedId = categorySummary.categoryImageResourceId
    )

    Divider(modifier = modifier.padding(3.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ColumnNoteAndCategory(record = record, modifier = modifier)

        ColumnAmountAndDate(record = record, modifier = modifier)

    }

}


@Composable
fun ExpenseToggleRow(
    modifier: Modifier = Modifier,
    statisticsViewModel: StatisticsViewModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        AppButtonToggleGroup(buttons = listOf(
            AppButton(
                title = AppConstants.EXPENSE,
                isIconButton = false,
                callback = {
                    statisticsViewModel.updateCategoryType(AppConstants.EXPENSE)
                }
            ),
            AppButton(
                title = AppConstants.INCOME,
                isIconButton = false,
                callback = {
                    statisticsViewModel.updateCategoryType(AppConstants.INCOME)
                }
            )

        ))


    }

}



