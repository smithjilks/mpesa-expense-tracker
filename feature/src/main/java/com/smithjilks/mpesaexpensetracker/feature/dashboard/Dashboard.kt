package com.smithjilks.mpesaexpensetracker.feature.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smithjilks.mpesaexpensetracker.core.widgets.BottomNavigation
import com.smithjilks.mpesaexpensetracker.feature.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Dashboard(
    navController: NavController,
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {
        MainDashboardContent(dashboardViewModel)
    }

}

@Composable
fun MainDashboardContent(dashboardViewModel: DashboardViewModel) {

    IncomeAndExpenseSummaryRow(income = "2000", expense = "30000")

}

@Composable
fun IncomeAndExpenseSummaryRow(modifier: Modifier = Modifier, income: String, expense: String) {

    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IncomeExpenseCard(
            modifier = modifier,
            amount = income,
            image = R.drawable.income_icon,
            isIncome = true
        )

        IncomeExpenseCard(
            modifier = modifier,
            amount = expense,
            image = R.drawable.expense_icon,
            isIncome = false
        )

    }
}

@Composable
fun IncomeExpenseCard(
    modifier: Modifier = Modifier,
    amount: String,
    image: Int,
    isIncome: Boolean
) {

    val backgroundColor = if (isIncome) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.error


    ElevatedCard(
        modifier = modifier
            .background(color = Color.Transparent),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Row(
            modifier = modifier
                .background(color = backgroundColor)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Image(
                modifier = modifier.padding(horizontal = 8.dp),
                painter = painterResource(id = image),
                contentDescription = "Income  Image",
                contentScale = ContentScale.Fit
            )

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = if (isIncome) "Income" else "Expense",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.padding(horizontal = 8.dp),
                )

                Text(
                    "Ksh. $amount",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.padding(horizontal = 8.dp),
                )

            }
        }
    }
}

@Preview
@Composable
fun IncomeAndExpenseSummaryRowPreview() {
    IncomeAndExpenseSummaryRow(income = "3000", expense = "4000")
}
