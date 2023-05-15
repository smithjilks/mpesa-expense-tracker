package com.smithjilks.mpesaexpensetracker.core.utils

import androidx.core.util.PatternsCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object Utils {
    fun isEmailValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun formatAmount(amount: Double): String {
        return "%.2f".format(amount)
    }

    fun formatDate(timestamp: Int): String {
        val sdf = SimpleDateFormat("EEE, MMM d yyyy", Locale.UK)
        val date = Date(timestamp.toLong() * 1000)

        return sdf.format(date)
    }

    fun formatTime(timestamp: Int): String {
        val sdf = SimpleDateFormat("hh:mm", Locale.UK)
        val date = Date(timestamp.toLong() * 1000)

        return sdf.format(date)
    }

    fun daysAgo(timestamp: Int): String {
        val diff = kotlin.math.abs(Date().time.toInt() - timestamp)
        val days = (diff / (24 * 60 * 60 * 1000))

        return when (days) {
            0 -> {
                "Today"
            }
            1 -> {
                "Yesterday"
            }
            else -> {
                formatDate(timestamp)
            }
        }
    }
}