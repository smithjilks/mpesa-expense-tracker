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

}