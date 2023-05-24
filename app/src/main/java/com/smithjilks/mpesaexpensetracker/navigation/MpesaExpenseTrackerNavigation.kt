package com.smithjilks.mpesaexpensetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smithjilks.mpesaexpensetracker.feature.dashboard.Dashboard
import com.smithjilks.mpesaexpensetracker.core.constants.MpesaExpenseTrackerScreens
import com.smithjilks.mpesaexpensetracker.feature.records.RecordDetailsScreen
import com.smithjilks.mpesaexpensetracker.feature.splashscreen.SplashScreen

@Composable
fun MpesaExpenseTrackerNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MpesaExpenseTrackerScreens.SplashScreen.name
    ) {

        composable(MpesaExpenseTrackerScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(MpesaExpenseTrackerScreens.DashboardScreen.name) {
            Dashboard(navController = navController)
        }

        composable(MpesaExpenseTrackerScreens.StatisticsScreen.name) {

        }

        composable(MpesaExpenseTrackerScreens.RecordsScreen.name) {

        }


        val recordDetailsRoute = MpesaExpenseTrackerScreens.RecordDetailsScreen.name
        composable(
            "$recordDetailsRoute?recordId={recordId}",
            arguments = listOf(navArgument(name = "recordId") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { backStackEntry ->
            RecordDetailsScreen(
                navController = navController,
                recordId = backStackEntry.arguments?.getString("recordId")
            )

        }


    }


}
