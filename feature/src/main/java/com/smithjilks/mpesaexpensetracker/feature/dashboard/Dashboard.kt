package com.smithjilks.mpesaexpensetracker.feature.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.utils.Utils
import com.smithjilks.mpesaexpensetracker.core.widgets.BottomNavigation
import com.smithjilks.mpesaexpensetracker.feature.R
import java.util.Date


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
fun MainDashboardContent(dashboardViewModel: DashboardViewModel, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val recentRecords = dashboardViewModel.recentRecordsList.collectAsState().value

        val record1 = Record(
            0,
            "BXMASNJAS",
            "Received",
            "300.00",
            "20.00",
            "MY first Note",
            Date().time.toInt(),
            "",
            "",
            AppConstants.INCOME,
            R.drawable.transport_icon

        )

        val record2 = Record(
            0,
            "BXMAUNJAS",
            "Shopping",
            "270.00",
            "20.00",
            "MY first Note",
            Date().time.toInt(),
            "",
            "",
            AppConstants.EXPENSE,
            R.drawable.shopping_icon
        )

        val record3 = Record(
            0,
            "BXMAYUNJAS",
            "Shopping",
            "290.00",
            "10.00",
            "MY first Note",
            Date().time.toInt(),
            "",
            "",
            AppConstants.EXPENSE,
            R.drawable.shopping_icon
        )

        val record4 = Record(
            0,
            "BXQAAYNJAS",
            "Electricity",
            "9990.00",
            "390.00",
            "MY first Note",
            Date().time.toInt(),
            "",
            "",
            AppConstants.EXPENSE,
            R.drawable.shopping_icon
        )


        //dashboardViewModel.insertRecords(listOf(record1, record2, record3, record4, record2))

        IncomeAndExpenseSummaryRow(income = Utils.formatAmount(dashboardViewModel.totalIncome),
            expense = Utils.formatAmount(dashboardViewModel.totalExpenses))

        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start,
            modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )

        LazyColumn {
            items(recentRecords) {
                RecordSummaryRow(
                    it
                )
            }
        }

    }


}

@Composable
fun IncomeAndExpenseSummaryRow(modifier: Modifier = Modifier, income: String, expense: String) {

    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(color = Color.Transparent),
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
            .background(color = Color.Transparent)
            .padding(horizontal = 8.dp),
        shape = CardDefaults.elevatedShape,
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Row(
            modifier = modifier
                .background(color = backgroundColor)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
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
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.padding(end = 8.dp),
                )

                Text(
                    "Ksh. $amount",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.End,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = modifier.padding(end = 8.dp),
                )

            }
        }
    }
}


@Composable
fun RecordSummaryRow(record: Record, modifier: Modifier = Modifier) {

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
fun ColumnAmountAndDate(record: Record, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${if (record.recordType == AppConstants.INCOME) "+" else "-"}Ksh. ${
                Utils.sumAmountAndTransactionCost(
                    record.amount,
                    record.transactionCost
                )
            }",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.End,
            color = if (record.recordType == AppConstants.INCOME) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.error,
            modifier = modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = Utils.formatDate(record.timestamp),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.End,
            color = Color.LightGray,
            modifier = modifier.padding(horizontal = 8.dp)
        )

    }

}

@Composable
fun ColumnNoteAndCategory(record: Record, modifier: Modifier = Modifier) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Transparent
        ) {

            Icon(
                imageVector = ImageVector.vectorResource(record.recordImageResourceId?: R.drawable.default_expense_icon),
                contentDescription = "${record.category} Icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = record.category,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                color = Color.Black,
                modifier = modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = record.note,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Start,
                color = Color.LightGray,
                modifier = modifier.padding(horizontal = 8.dp)
            )

        }
    }

}

@Preview
@Composable
fun IncomeAndExpenseSummaryRowPreview() {
    IncomeAndExpenseSummaryRow(income = "3000", expense = "4000")
}

@Preview
@Composable
fun RecordSummaryRowPreview() {
    val record = Record(
        1,
        "BXMASNJAS",
        "Transport",
        "300.00",
        "20.00",
        "MY first Note",
        Date().time.toInt(),
        "",
        "",
        AppConstants.INCOME,
        R.drawable.transport_icon
    )

    LazyColumn {
        items(listOf(record, record, record, record)) {
            RecordSummaryRow(
                it
            )
        }
    }
}
