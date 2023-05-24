package com.smithjilks.mpesaexpensetracker.core.utils

import androidx.core.util.PatternsCompat
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.Record
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
        val sdf = SimpleDateFormat("EEE, MMM d yyyy", Locale.getDefault())
        val date = Date(timestamp.toLong() * 1000)

        return sdf.format(date)
    }

    fun formatTime(timestamp: Int): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = Date(timestamp.toLong() * 1000)

        // DateTimeFormatter is more Thread safe approach however requires > API Level 26
        // SimpleDateFormat is not designed to support multpile threads
        // thus can lead to inconsistent results when the instance is shared across
        // multiple threads

//        DateTimeFormatter.ofPattern("hh:mm", Locale.getDefault())
//            .format(Instant.ofEpochMilli(timestamp.toLong() * 1000))

        return sdf.format(date)
    }

    fun formatDate(dateMillis: Long): String {
        val sdf = SimpleDateFormat("dd/M/yy", Locale.getDefault())
        val date = Date(dateMillis)
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

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberRegex = "^(01|07)[0-9]{8}"
        return phoneNumber.trim().matches(Regex(phoneNumberRegex))
    }

    fun sumAmountAndTransactionCost(amount: String, transactionCost: String): String {
        return formatAmount(amount.toDouble() + transactionCost.toDouble())
    }

    fun createRecordFromMpesaMessage(message: String): Record? {

        val transactionRefRegex = "\\b[A-Z0-9]+\\b".toRegex()
        val amountRegex = "(Ksh)(\\d+,|)\\d+(\\.\\d{2})".toRegex()
        val dateRegex = "\\d+/\\d+/\\d+".toRegex()
        val timeRegex = "\\d+:\\d+ (PM|AM)".toRegex()
        val withdrawalFromRegex = "(from).+(New M-PESA)".toRegex()
        val buyGoodsPaidToRegex = "(paid to).+(\\. on)".toRegex()
        val sentToRegex = "(sent to).+( on \\d+/)".toRegex()
        val receivedFromRegex = "(from).+( on \\d+/)".toRegex()


        //Find amount, transaction cost, balance


        val transactionRef = transactionRefRegex.find(message)?.value ?: return null

        val amounts = amountRegex.findAll(message).toList()
        val timestamp = convertDateAndTimeToTimestamp(
            dateRegex.find(message)?.value,
            timeRegex.find(message)?.value
        )



        return  when {
            //Handle withdrawal message
            message.contains("Withdraw") -> {

                val payeeLength = withdrawalFromRegex.find(message)?.value?.length ?: 0
                val payee = withdrawalFromRegex.find(message)?.value

                 Record(
                    transactionRef = transactionRef,
                    category = AppConstants.DEFAULT_CATEGORY,
                    amount = amounts[0].value.substring(3),
                    transactionCost = try {
                        amounts[2].value.substring(3)
                    } catch (e: Exception) {
                        "0.00"
                    },
                    note = "Withdrawal",
                    timestamp = timestamp,
                    account = "",
                    payee = payee?.subSequence(5, payeeLength - 10)
                        .toString(),
                    recordType = AppConstants.EXPENSE,
                    recordImageResourceId = null
                )
            }

            //Handle deposit
            message.contains("Give") -> {
                TODO()
            }

            //Handle reversals
            message.contains("Give") -> {
                TODO()
            }

            //Handle Received money message
            message.contains("received") -> {
                val payeeLength = receivedFromRegex.find(message)?.value?.length ?: 0
                val payee = receivedFromRegex.find(message)?.value

                Record(
                    transactionRef = transactionRef,
                    category = AppConstants.RECEIVED_MONEY,
                    amount = amounts[0].value.substring(3),
                    transactionCost = "0.00",
                    note = "Received cash",
                    timestamp = timestamp,
                    account = "",
                    payee = payee?.subSequence(5, payeeLength - 7)
                        .toString(),
                    recordType = AppConstants.INCOME,
                    recordImageResourceId = null
                )

            }

            //Handle buy goods message
            message.contains("paid to") -> {
                val payeeLength = buyGoodsPaidToRegex.find(message)?.value?.length ?: 0
                val payee = buyGoodsPaidToRegex.find(message)?.value

                Record(
                    transactionRef = transactionRef,
                    category = AppConstants.DEFAULT_CATEGORY,
                    amount = amounts[0].value.substring(3),
                    transactionCost = try {
                        amounts[2].value.substring(3)
                    } catch (e: Exception) {
                        "0.00"
                    },
                    note = "Buy Goods...",
                    timestamp = timestamp,
                    account = "",
                    payee = payee?.subSequence(8, payeeLength - 3)
                        .toString(),
                    recordType = AppConstants.EXPENSE,
                    recordImageResourceId = null
                )
            }

            //Handle paybill message
            message.contains("for account") -> {
                val payeeLength = sentToRegex.find(message)?.value?.length ?: 0
                val payee = sentToRegex.find(message)?.value

                Record(
                    transactionRef = transactionRef,
                    category = AppConstants.DEFAULT_CATEGORY,
                    amount = amounts[0].value.substring(3),
                    transactionCost = try {
                        amounts[2].value.substring(3)
                    } catch (e: Exception) {
                        "0.00"
                    },
                    note = "Pay Bill...",
                    timestamp = timestamp,
                    account = "",
                    payee = payee?.subSequence(8, payeeLength - 7)
                        .toString(),
                    recordType = AppConstants.EXPENSE,
                    recordImageResourceId = null
                )
            }

            // Send money
            message.contains("sent to") && !message.contains("for account") -> {
                val payeeLength = sentToRegex.find(message)?.value?.length ?: 0
                val payee = sentToRegex.find(message)?.value

                Record(
                    transactionRef = transactionRef,
                    category = AppConstants.DEFAULT_CATEGORY,
                    amount = amounts[0].value.substring(3),
                    transactionCost = try {
                        amounts[2].value.substring(3)
                    } catch (e: Exception) {
                        "0.00"
                    },
                    note = "Send Money...",
                    timestamp = timestamp,
                    account = "",
                    payee = payee?.subSequence(8, payeeLength - 7)
                        .toString(),
                    recordType = AppConstants.EXPENSE,
                    recordImageResourceId = null
                )
            }

            //Handle airtime message
            message.contains("airtime") -> {
                Record(
                    transactionRef = transactionRef,
                    category = AppConstants.AIRTIME,
                    amount = amounts[0].value.substring(3),
                    transactionCost = try {
                        amounts[2].value.substring(3)
                    } catch (e: Exception) {
                        "0.00"
                    },
                    note = "Buy Airtime...",
                    timestamp = timestamp,
                    account = "",
                    payee = "Myself",
                    recordType = AppConstants.EXPENSE,
                    recordImageResourceId = null
                )
            }

            else -> {null}
        }
    }

    fun convertDateAndTimeToTimestamp(date: String?, time: String?): Int {
        // date is in the format 20/5/23
        // time is in the format 4:03 PM

        if (date != null && time != null) {
            val format = SimpleDateFormat("dd/M/yy hh:mm a", Locale.UK)
            val newDate = format.parse("$date $time")
            return newDate.time.div(1000L).toInt()
        }
        return 0
    }
}