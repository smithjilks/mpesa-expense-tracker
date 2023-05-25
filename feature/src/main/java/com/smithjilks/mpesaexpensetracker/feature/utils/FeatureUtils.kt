package com.smithjilks.mpesaexpensetracker.feature.utils

import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.feature.R

object FeatureUtils {
    fun getCategoriesList(): List<Category> {
        return listOf(
            Category(name = "General Shopping", imageId = R.drawable.shopping_icon),

            Category(name = "Airtime", imageId = R.drawable.airtime_icon),
            Category(name = "WiFi", imageId = R.drawable.wifi_icon),
            Category(name = "Data Bundles", imageId = R.drawable.data_bundle_icon),


            Category(name = "Groceries", imageId = R.drawable.grocery_icon),
            Category(name = "Restaurant, fast-food", imageId = R.drawable.restaurant_icon),
            Category(name = "Liquor", imageId = R.drawable.liquor_icon),
            Category(name = "Bar, Cafe", imageId = R.drawable.bar_icon),

            Category(name = "Clothes and Shoes", imageId = R.drawable.clothes_icon_2),
            Category(name = "Health and Beauty", imageId = R.drawable.health_and_beauty_icon),
            Category(name = "Electronics", imageId = R.drawable.electronics_icon),
            Category(name = "Gifts", imageId = R.drawable.gift_icon),
            Category(name = "Stationery", imageId = R.drawable.stationery_icon),
            Category(name = "Chemist, Drug Store", imageId = R.drawable.drugs_icon),

            Category(name = "Rent", imageId = R.drawable.rent_icon),
            Category(name = "Energy and Utilities", imageId = R.drawable.energy_utilities_icon),
            Category(name = "Maintenance, Repairs", imageId = R.drawable.maintenance_repair_icon),


            Category(name = "Public Transport", imageId = R.drawable.public_transport_icon),
            Category(name = "Taxi, Cab", imageId = R.drawable.taxi_icon),


            Category(name = "Fuel", imageId = R.drawable.fuel_icon),
            Category(name = "Parking", imageId = R.drawable.parking_icon),
            Category(name = "Vehicle Maintenance", imageId = R.drawable.vehicle_maintenance_icon),
            Category(name = "Vehicle Rentals", imageId = R.drawable.vehicle_rental),
            Category(name = "Vehicle Insurance", imageId = R.drawable.insurance_icon),

            Category(name = "Healthcare", imageId = R.drawable.health_icon),
            Category(name = "Wellness, Beauty", imageId = R.drawable.beauty_icon),
            Category(name = "Fitness", imageId = R.drawable.fitness_icon),
            Category(name = "Life Events, Birthdays, Funeral", imageId = R.drawable.events_icon),
            Category(name = "Education", imageId = R.drawable.education_icon),
            Category(name = "Subscriptions, Streaming", imageId = R.drawable.subscriptions_icon),
            Category(name = "Holiday, Trips", imageId = R.drawable.holiday_icon),

            Category(name = "Software, Apps, Games", imageId = R.drawable.games_icon),

            Category(name = "Loans, Interests", imageId = R.drawable.income_arrow_icon),
            Category(name = "Taxes", imageId = R.drawable.money_icon),
            Category(name = "Fines, Charges", imageId = R.drawable.insurance_icon),
            Category(name = "Child Support", imageId = R.drawable.child_support_icon),

            Category(name = "Savings", imageId = R.drawable.savings_icon),
            Category(name = "Money Market Fund", imageId = R.drawable.money_icon),
            Category(name = "Collections, Chama", imageId = R.drawable.groups_icon),

            Category(name = "Sales", imageId = R.drawable.sales_icon),
            Category(name = "Wages, Invoices", imageId = R.drawable.income_arrow_icon),
            Category(name = "Money Gifts", imageId = R.drawable.gift_icon),
            Category(name = "Rental Income", imageId = R.drawable.rental_icon),
            Category(name = "Reversals", imageId = R.drawable.reversal_icon),
            Category(name = "Interests, Dividends", imageId = R.drawable.invest_icon),
        )
    }

}