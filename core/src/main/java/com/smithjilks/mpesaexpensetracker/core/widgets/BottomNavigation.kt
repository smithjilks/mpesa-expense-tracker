package com.smithjilks.mpesaexpensetracker.core.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
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
            name = "New Record",
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                border = BorderStroke(width = 0.25.dp, color = Color.LightGray),
                shape = RoundedCornerShape(4.dp)
            ),
        containerColor = Color.Transparent,
        tonalElevation = 8.dp
    ) {
        bottomNavItems.forEach { item ->

            val selected = item.route ==
                    navController.currentBackStackEntryAsState().value?.destination?.route

            NavigationBarItem(
                modifier = Modifier.padding(0.dp),
                selected = false,
                onClick = {
                    navController.navigate(item.route) {

                        popUpTo(MpesaExpenseTrackerScreens.DashboardScreen.name) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },
                label = {
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = item.name,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                            .size(30.dp),
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                        tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
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
