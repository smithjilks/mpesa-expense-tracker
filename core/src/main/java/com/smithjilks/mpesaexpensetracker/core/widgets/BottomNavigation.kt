package com.smithjilks.mpesaexpensetracker.core.widgets

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.smithjilks.mpesaexpensetracker.core.R
import com.smithjilks.mpesaexpensetracker.core.constants.MpesaExpenseTrackerScreens

data class BottomNavigationItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)


@Composable
fun BottomNavigation(navController: NavController) {

    val bottomNavItems = listOf(
        BottomNavigationItem(
            name = "Dashboard",
            route = MpesaExpenseTrackerScreens.DashboardScreen.name,
            icon = ImageVector.vectorResource(R.drawable.dashboard_icon)
        ),
        BottomNavigationItem(
            name = "Create",
            route = MpesaExpenseTrackerScreens.RecordDetailsScreen.name,
            icon = ImageVector.vectorResource(R.drawable.create_record_icon)
        ),
        BottomNavigationItem(
            name = "Statistics",
            route = MpesaExpenseTrackerScreens.StatisticsScreen.name,
            icon = ImageVector.vectorResource(R.drawable.statistics_icon)
        )
    )

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        bottomNavItems.forEach { item ->

            val selected = item.route ==
                    navController.currentBackStackEntryAsState().value?.destination?.route

            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(item.route){

                    popUpTo(MpesaExpenseTrackerScreens.DashboardScreen.name) {
                        saveState = true
                    }

                    launchSingleTop = true

                    restoreState = true
                } },
                label = {
                    Text(
                        modifier = Modifier.padding(0.dp),
                        text = item.name,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .padding(0.dp)
                            .size(30.dp),
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                        tint = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    }

}

@Composable
fun CreateRecordFab(navController: NavController, route: String) {
    FloatingActionButton(
        modifier = Modifier,
        shape = CircleShape,
        interactionSource = remember { MutableInteractionSource() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        elevation = FloatingActionButtonDefaults.elevation(),
        onClick = {
            navController.navigate(route)
        }) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.create_record_icon),
            contentDescription = "Create new record icon",
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}
