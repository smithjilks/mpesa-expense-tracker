package com.smithjilks.mpesaexpensetracker.core.utils

import androidx.core.util.PatternsCompat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
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

        // DateTimeFormatter is more Thread safe approach however requires > API Level 26
        // SimpleDateFormat is not designed to support multpile threads
        // thus can lead to inconsistent results when the instance is shared across
        // multiple threads

//        DateTimeFormatter.ofPattern("hh:mm", Locale.getDefault())
//            .format(Instant.ofEpochMilli(timestamp.toLong() * 1000))

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