package com.smithjilks.mpesaexpensetracker.feature.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.smithjilks.mpesaexpensetracker.core.widgets.BottomNavigation


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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        )
    }

}