package com.smithjilks.mpesaexpensetracker.feature.utils

import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.feature.R

object FeatureUtils {
    val categoriesList: List<Category> = listOf(
        Category(name = "General Shopping", resId = R.drawable.shopping_icon),

        Category(name = "Airtime", resId = R.drawable.airtime_icon),
        Category(name = "WiFi", resId = R.drawable.wifi_icon),
        Category(name = "Data Bundles", resId = R.drawable.data_bundle_icon),


        Category(name = "Groceries", resId = R.drawable.grocery_icon),
        Category(name = "Restaurant, fast-food", resId = R.drawable.restaurant_icon),
        Category(name = "Liquor", resId = R.drawable.liquor_icon),
        Category(name = "Bar, Cafe", resId = R.drawable.bar_icon),

        Category(name = "Clothes and Shoes", resId = R.drawable.clothes_icon_2),
        Category(name = "Health and Beauty", resId = R.drawable.health_and_beauty_icon),
        Category(name = "Electronics", resId = R.drawable.electronics_icon),
        Category(name = "Gifts", resId = R.drawable.gift_icon),
        Category(name = "Stationery", resId = R.drawable.stationery_icon),
        Category(name = "Chemist, Drug Store", resId = R.drawable.drugs_icon),

        Category(name = "Rent", resId = R.drawable.rent_icon),
        Category(name = "Energy and Utilities", resId = R.drawable.energy_utilities_icon),
        Category(name = "Maintenance, Repairs", resId = R.drawable.maintenance_repair_icon),


        Category(name = "Public Transport", resId = R.drawable.public_transport_icon),
        Category(name = "Taxi, Cab", resId = R.drawable.taxi_icon),


        Category(name = "Fuel", resId = R.drawable.fuel_icon),
        Category(name = "Parking", resId = R.drawable.parking_icon),
        Category(name = "Vehicle Maintenance", resId = R.drawable.vehicle_maintenance_icon),
        Category(name = "Vehicle Rentals", resId = R.drawable.vehicle_rental),
        Category(name = "Vehicle Insurance", resId = R.drawable.insurance_icon),

        Category(name = "Healthcare", resId = R.drawable.health_icon),
        Category(name = "Wellness, Beauty", resId = R.drawable.beauty_icon),
        Category(name = "Fitness", resId = R.drawable.fitness_icon),
        Category(name = "Life Events, Birthdays, Funeral", resId = R.drawable.events_icon),
        Category(name = "Education", resId = R.drawable.education_icon),
        Category(name = "Subscriptions, Streaming", resId = R.drawable.subscriptions_icon),
        Category(name = "Holiday, Trips", resId = R.drawable.holiday_icon),

        Category(name = "Software, Apps, Games", resId = R.drawable.games_icon),

        Category(name = "Loans, Interests", resId = R.drawable.income_arrow_icon),
        Category(name = "Taxes", resId = R.drawable.money_icon),
        Category(name = "Fines, Charges", resId = R.drawable.insurance_icon),
        Category(name = "Child Support", resId = R.drawable.child_support_icon),

        Category(name = "Savings", resId = R.drawable.savings_icon),
        Category(name = "Money Market Fund", resId = R.drawable.money_icon),
        Category(name = "Collections, Chama", resId = R.drawable.groups_icon),

        Category(name = "Sales", resId = R.drawable.sales_icon),
        Category(name = "Wages, Invoices", resId = R.drawable.income_arrow_icon),
        Category(name = "Money Gifts", resId = R.drawable.gift_icon),
        Category(name = "Rental Income", resId = R.drawable.rental_icon),
        Category(name = "Reversals", resId = R.drawable.reversal_icon),
        Category(name = "Interests, Dividends", resId = R.drawable.invest_icon),
    )

    val recordTypesList: List<Category> = listOf(
        Category(
            name = AppConstants.INCOME,
            resId = R.drawable.income_arrow_icon,
            stored = null
        ),
        Category(
            name = AppConstants.EXPENSE,
            resId = R.drawable.expense_arrow_icon,
            stored = null
        )
    )

}