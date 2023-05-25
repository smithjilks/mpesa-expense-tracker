package com.smithjilks.mpesaexpensetracker.feature.records

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNodeLifecycleCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.constants.MpesaExpenseTrackerScreens
import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.core.widgets.AppDatePickerDialog
import com.smithjilks.mpesaexpensetracker.core.widgets.AppFilterChipsGroup
import com.smithjilks.mpesaexpensetracker.core.widgets.AppInputTextField
import com.smithjilks.mpesaexpensetracker.core.widgets.AppSpinner
import com.smithjilks.mpesaexpensetracker.core.widgets.BottomNavigation
import com.smithjilks.mpesaexpensetracker.feature.R
import com.smithjilks.mpesaexpensetracker.feature.dashboard.RecordSummaryRow
import com.smithjilks.mpesaexpensetracker.feature.utils.FeatureUtils
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecordsScreen(
    navController: NavController,
    recordsViewModel: RecordsViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(navController)
        }
    ) {
        MainRecordsContent(
            recordsViewModel = recordsViewModel,
            navController = navController
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainRecordsContent(
    modifier: Modifier = Modifier,
    recordsViewModel: RecordsViewModel,
    navController: NavController
) {

    var filterButtonChecked by remember { mutableStateOf(false) }
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var filterQuery by rememberSaveable { mutableStateOf("") }


    val allRecords = recordsViewModel.allRecordsList.collectAsState().value

    filterButtonChecked = filterQuery.isNotEmpty()


    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            OutlinedIconToggleButton(
                border = BorderStroke(
                    width = 0.25.dp,
                    color = Color.LightGray
                ),
                shape = RoundedCornerShape(4.dp),
                colors = IconButtonDefaults.filledTonalIconToggleButtonColors(),
                checked = filterButtonChecked,
                onCheckedChange = {
                    filterButtonChecked = it
                    openBottomSheet = it

                    if (!it) {
                        filterQuery = ""
                    }
                }
            ) {
                if (filterButtonChecked) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.filter_icon),
                        contentDescription = "Filter icon"
                    )
                } else {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.filter_off_icon),
                        contentDescription = "No filter icon "
                    )
                }
            }

        }

        LazyColumn {
            items(allRecords) {
                RecordSummaryRow(
                    modifier = Modifier.clickable {
                        navController.navigate(
                            "${MpesaExpenseTrackerScreens.RecordDetailsScreen.name}?recordId=${it.id}"
                        )
                    },
                    record = it,
                )
            }
        }

    }

    FilterBottomSheet(
        open = openBottomSheet,
        onDismissBottomSheet = { openBottomSheet = !openBottomSheet },
        onFilterQueryChange = { filterQuery = it }
    )


}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    open: Boolean,
    onDismissBottomSheet: (Boolean) -> Unit,
    onFilterQueryChange: (String) -> Unit = {}
) {

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var openDatePicker by remember { mutableStateOf(false) }
    var isStartDate by remember { mutableStateOf(false) }


    var skipPartiallyExpanded by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val scope = rememberCoroutineScope()

    var selectedFilterChip by remember { mutableStateOf("") }
    var selectedSortByChip by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }



    if (open) {
        ModalBottomSheet(
            onDismissRequest = { onDismissBottomSheet(open) },
            sheetState = bottomSheetState
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Recent Transactions",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )

                FilledTonalButton(
                    onClick = {
                        selectedFilterChip = ""
                        selectedSortByChip = ""
                        startDate = ""
                        endDate = ""
                    },
                    shape = ButtonDefaults.elevatedShape,
                    colors = ButtonDefaults.filledTonalButtonColors()
                ) {
                    Text(
                        text = "Reset",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

            }


            Text(
                text = "Filter By",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(horizontal = 10.dp),
                fontWeight = FontWeight.Bold
            )

            // Filter by grid
            AppFilterChipsGroup(
                modifier = modifier.padding(horizontal = 10.dp),
                chipLabels = listOf(AppConstants.INCOME, AppConstants.EXPENSE),
                selectedChip = selectedFilterChip,
                onSelectedChange = { selectedFilterChip = it }
            )

            Text(
                text = "Sort By",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(horizontal = 10.dp),
                fontWeight = FontWeight.Bold
            )

            // Sort by grid
            AppFilterChipsGroup(
                modifier = modifier.padding(horizontal = 10.dp),
                chipLabels = listOf("Highest", "Lowest", "Newest", "Oldest"),
                selectedChip = selectedSortByChip,
                onSelectedChange = { selectedSortByChip = it }
            )

            Text(
                text = "Date",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(horizontal = 10.dp),
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppInputTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            openDatePicker = true
                            isStartDate = true
                        },
                    isEnabled = false,
                    text = startDate,
                    label = "Start",
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                R.drawable.date_icon
                            ),
                            contentDescription = "Date Icon"
                        )
                    },
                    onTextChange = { startDate = it }) {
                }

                AppInputTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            openDatePicker = true
                            isStartDate = false
                        },
                    isEnabled = false,
                    text = endDate,
                    label = "End",
                    trailingIcon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(
                                R.drawable.date_icon
                            ),
                            contentDescription = "Date Icon"
                        )
                    },
                    onTextChange = { endDate = it }) {
                }

            }


            Text(
                text = "Category",
                style = MaterialTheme.typography.bodyLarge,
                modifier = modifier.padding(horizontal = 10.dp),
                fontWeight = FontWeight.Bold
            )
            //Categories spinner
            AppSpinner(
                modifier = modifier.padding(bottom = 8.dp),
                label = "",
                parentOptions = FeatureUtils.getCategoriesList(),
                onValueChange = { selectedCategory = it }
            )



            Button(
                modifier = modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                ),
                // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                // you must additionally handle intended state cleanup, if any.
                onClick = {
                    onFilterQueryChange("Query")
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) {
                            onDismissBottomSheet(open)
                        }
                    }
                }
            ) {
                Text(
                    text = "Continue",
                    style = MaterialTheme.typography.bodyMedium
                )
            }


        }
    }

    AppDatePickerDialog(
        openDatePicker,
        onValueChange = {
            it?.let {  date ->
                if (isStartDate) startDate = date else endDate = date
            }
            openDatePicker = false
        }
    )

}



