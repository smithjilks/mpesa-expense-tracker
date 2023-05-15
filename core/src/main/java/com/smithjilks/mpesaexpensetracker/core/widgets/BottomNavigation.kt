package com.smithjilks.mpesaexpensetracker.core.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavigationItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavigationItem(
        name = "Home",
        route = "home",
        icon = Icons.Rounded.Home,
    ),
    BottomNavigationItem(
        name = "Create",
        route = "add",
        icon = Icons.Rounded.AddCircle,
    ),
    BottomNavigationItem(
        name = "Settings",
        route = "settings",
        icon = Icons.Rounded.Settings,
    ),
)

@Composable
fun BottomNavigation(navController: NavController) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        bottomNavItems.forEach{ item ->

            val selected = item.route ==
                    navController.currentBackStackEntryAsState().value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(item.route) },
                label = {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.name} Icon",
                    )
                }
            )
        }
    }

}