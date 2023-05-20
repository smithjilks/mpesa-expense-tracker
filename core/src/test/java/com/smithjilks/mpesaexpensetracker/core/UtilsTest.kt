package com.smithjilks.mpesaexpensetracker.core

import com.smithjilks.mpesaexpensetracker.core.utils.Utils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.util.Date

class UtilsTest {
    @Test
    fun testFormatDecimals() {
        val amount = 100.0
        assertTrue("100.00" == Utils.formatAmount(amount))
        assertFalse("100.000" == Utils.formatAmount(amount))
        assertFalse("100.0" == Utils.formatAmount(amount))
        assertFalse("100" == Utils.formatAmount(amount))
    }

    @Test
    fun testFormatDateTime() {
        assertTrue("Fri, May 12 2023" == Utils.formatDate(1683898085))
        assertTrue("04:28" == Utils.formatTime(1683898085))

    }

    @Test
    fun testDaysAgo() {
        val MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24

        assertTrue("Fri, May 12 2023" == Utils.daysAgo(1683898085))
        assertTrue("Today" == Utils.daysAgo(Date().time.toInt()))
        assertFalse("Yesterday" == Utils.daysAgo(Date().time.toInt()))
        assertTrue("Yesterday" == Utils.daysAgo((Date().time - MILLIS_IN_A_DAY).toInt()))
        assertFalse("Today" == Utils.daysAgo((Date().time - MILLIS_IN_A_DAY).toInt()))

    }

    @Test
    fun testIsPhoneNumberValid() {
        assertTrue(Utils.isValidPhoneNumber("0712345678"))
        assertTrue(Utils.isValidPhoneNumber("0112345678"))
        assertFalse(Utils.isValidPhoneNumber("254712345678"))
        assertFalse(Utils.isValidPhoneNumber("254712345678"))
        assertFalse(Utils.isValidPhoneNumber("+2540712345678"))
        assertFalse(Utils.isValidPhoneNumber("071235678c"))
        assertFalse(Utils.isValidPhoneNumber("0712345678g"))
        assertFalse(Utils.isValidPhoneNumber("71234568"))
        assertFalse(Utils.isValidPhoneNumber("7123456897"))
    }

    @Test
    fun testSumAmountAndTransactionCost() {
        assertTrue("120.00" == Utils.sumAmountAndTransactionCost("100", "20"))
        assertFalse("110.00" == Utils.sumAmountAndTransactionCost("100", "20"))

    }

}