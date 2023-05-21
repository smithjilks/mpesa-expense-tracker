package com.smithjilks.mpesaexpensetracker.core

import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.utils.Utils
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
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

    @Test
    fun testConvertDateAndTimeToTimestamp() {
        assertTrue(1684587780 == Utils.convertDateAndTimeToTimestamp("20/5/23", " 4:03 PM"))
        assertTrue(0 == Utils.convertDateAndTimeToTimestamp(null, null))

    }


    @Test
    fun testCreateRecordFromMpesaMessage() {
        val withdrawalRecord = Record(
            0,
            transactionRef = "REK4ZY2YCC",
            category = AppConstants.DEFAULT_CATEGORY,
            amount = "600.00",
            transactionCost = "28.00",
            note = "Withdrawal",
            timestamp = Utils.convertDateAndTimeToTimestamp("20/5/23", "4:32 PM"),
            account = "",
            payee = "353403 - New Nairobi-Naivasha First communication Nairobi-Mai Mahiu stage ",
            recordType = AppConstants.EXPENSE,
            recordImageResourceId = null
        )

        val createdWithdrawalRecord = Utils.createRecordFromMpesaMessage(
            "REK4ZY2YCC Confirmed.on 20/5/23 at 4:32 PMWithdraw Ksh600.00 " +
                    "from 353403 - New Nairobi-Naivasha First communication Nairobi-Mai Mahiu stage New M-PESA balance is Ksh8.91. " +
                    "Transaction cost, Ksh28.00. Amount you can transact within the day is 298,995.00. " +
                    "Withdraw your Fuliza limit at any M-Pesa agent Click https://bit.ly/3rhk97G or dial *334#"
        )

        val createdWithdrawalRecord2 = Utils.createRecordFromMpesaMessage(
            "REK4ZY2YCC Confirmed.on 20/5/23 at 4:32 PM Ksh600.00 " +
                    "from 353403 - New Nairobi-Naivasha First communication Nairobi-Mai Mahiu stage New M-PESA balance is Ksh8.91. " +
                    "Transaction cost, Ksh28.00. Amount you can transact within the day is 298,995.00. " +
                    "Withdraw your Fuliza limit at any M-Pesa agent Click https://bit.ly/3rhk97G or dial *334#"
        )
        assertTrue(withdrawalRecord == createdWithdrawalRecord)
        assertTrue(withdrawalRecord == createdWithdrawalRecord2)


        val receivedRecord = Record(
            0,
            transactionRef = "REK5ZV0P9P",
            category = AppConstants.RECEIVED_MONEY,
            amount = "100.00",
            transactionCost = "0.00",
            note = "Received cash",
            timestamp = Utils.convertDateAndTimeToTimestamp("20/5/23", "4:03 PM"),
            account = "",
            payee = "STANDARD CHARTERED BANK 329299",
            recordType = AppConstants.INCOME,
            recordImageResourceId = null
        )

        val createdReceivedRecord = Utils.createRecordFromMpesaMessage(
            "REK5ZV0P9P Confirmed." +
                    "You have received Ksh100.00 from STANDARD CHARTERED BANK 329299 on 20/5/23 " +
                    "at 4:03 PM New M-PESA balance is Ksh16.91.  " +
                    "Separate personal and business funds through Pochi la Biashara on *334#."
        )

        val createdReceivedRecord2 = Utils.createRecordFromMpesaMessage(
            "REK5ZV0P9P Confirmed." +
                    "You have  Ksh100.00 from STANDARD CHARTERED BANK 329299 on 20/5/23 " +
                    "at 4:03 PM New M-PESA balance is Ksh16.91.  " +
                    "Separate personal and business funds through Pochi la Biashara on *334#."
        )
        assertTrue(receivedRecord == createdReceivedRecord)
        assertTrue(null == createdReceivedRecord2)


        val buyGoodsRecord = Record(
            0,
            transactionRef = "REK61DLFRE",
            category = AppConstants.DEFAULT_CATEGORY,
            amount = "100.00",
            transactionCost = "0.00",
            note = "Buy Goods...",
            timestamp = Utils.convertDateAndTimeToTimestamp("20/5/23", "6:39 PM"),
            account = "",
            payee = "Naivas Safari Centre Naivasha.",
            recordType = AppConstants.EXPENSE,
            recordImageResourceId = null
        )

        val createdBuyGoodsRecord = Utils.createRecordFromMpesaMessage(
            "REK61DLFRE Confirmed. Ksh100.00 paid to Naivas Safari Centre Naivasha. on 20/5/23 at 6:39 PM." +
                    "New M-PESA balance is Ksh100.00. Transaction cost, Ksh0.00. " +
                    "Amount you can transact within the day is 297,728.00. " +
                    "Pay to POCHI LA BIASHARA FREE for amount below Kshs 100."
        )
        assertTrue(buyGoodsRecord == createdBuyGoodsRecord)


        val payBillRecord = Record(
            transactionRef = "REL83EC6RG",
            category = AppConstants.DEFAULT_CATEGORY,
            amount = "2,500.00",
            transactionCost = "0.00",
            note = "Pay Bill...",
            timestamp = Utils.convertDateAndTimeToTimestamp("21/5/23", "2:59 PM"),
            account = "",
            payee = "KPLC PREPAID for account 37228894004",
            recordType = AppConstants.EXPENSE,
            recordImageResourceId = null
        )

        val createdPayBillRecord = Utils.createRecordFromMpesaMessage(
            "REL83EC6RG Confirmed. " +
                    "Ksh2,500.00 sent to KPLC PREPAID for account 37228894004 on 21/5/23 at 2:59 PM " +
                    "New M-PESA balance is Ksh90.91. Transaction cost, Ksh0.00." +
                    "Amount you can transact within the day is 122,899.00. " +
                    "Buy business stock with M-PESA GlobalPay virtual Visa card. " +
                    "Click on https://mpesaapp.page.link/GlobalPay"
        )
        assertTrue(payBillRecord == createdPayBillRecord)



        val sendMoneyRecord = Record(
            transactionRef = "REL93DKL7Z",
            category = AppConstants.DEFAULT_CATEGORY,
            amount = "1.00",
            transactionCost = "0.00",
            note = "Send Money...",
            timestamp = Utils.convertDateAndTimeToTimestamp("21/5/22", "1:51 PM"),
            account = "",
            payee = "JOHN  DOE 0712345678",
            recordType = AppConstants.EXPENSE,
            recordImageResourceId = null
        )

        val createdSendMoneyRecord = Utils.createRecordFromMpesaMessage(
            "REL93DKL7Z Confirmed. Ksh1.00 sent to JOHN  DOE 0712345678 on 21/5/22 at 1:51 PM. " +
                    "New M-PESA balance is Ksh0.00. Transaction cost, Ksh0.00. " +
                    "Amount you can transact within the day is 299,999.00. " +
                    "Use M-PESA app to send money conveniently mpesaapp.page.link/ggGV"
        )
        assertTrue(sendMoneyRecord == createdSendMoneyRecord)


        val buyAirtimeRecord = Record(
            transactionRef = "REK3YEJE2F",
            category = AppConstants.AIRTIME,
            amount = "300.00",
            transactionCost ="0.00",
            note = "Buy Airtime...",
            timestamp = Utils.convertDateAndTimeToTimestamp("20/5/22", "2:26 AM"),
            account = "",
            payee = "Myself",
            recordType = AppConstants.EXPENSE,
            recordImageResourceId = null
        )

        val createdBuyAirtimeRecord = Utils.createRecordFromMpesaMessage(
            "REK3YEJE2F confirmed.You bought Ksh300.00 of airtime on 20/5/22 at 2:26 AM." +
                    "New M-PESA balance is Ksh0.00. Transaction cost, Ksh0.00. " +
                    "Amount you can transact within the day is 129,400.00. " +
                    "Dial *234*0# to Opt in to FULIZA and check your limit."
        )
        assertTrue(buyAirtimeRecord == createdBuyAirtimeRecord)

    }


}