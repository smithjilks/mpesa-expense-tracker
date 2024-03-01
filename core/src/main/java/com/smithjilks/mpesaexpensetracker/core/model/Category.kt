package com.smithjilks.mpesaexpensetracker.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey
    val id: Int = 0,
    val name: String,
    val resId: Int?,
    val stored: Int? = calculateStoredValue()
) {
    companion object {
        private var nextStoredValue = 1

        private fun calculateStoredValue(): Int {
            val storedValue = nextStoredValue
            nextStoredValue++
            return storedValue
        }
    }
}
