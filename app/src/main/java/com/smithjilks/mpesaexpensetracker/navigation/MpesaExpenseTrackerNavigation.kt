package com.smithjilks.mpesaexpensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MpesaExpenseTrackerNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = MpesaExpenseTrackerScreens.SplashScreen.name) {

        composable(MpesaExpenseTrackerScreens.SplashScreen.name) {

        }

        composable(MpesaExpenseTrackerScreens.DashboardScreen.name) {

        }

        composable(MpesaExpenseTrackerScreens.StatisticsScreen.name) {

        }

        composable(MpesaExpenseTrackerScreens.RecordsScreen.name) {

        }

        composable(MpesaExpenseTrackerScreens.RecordDetailsScreen.name) {

        }


    }


}
