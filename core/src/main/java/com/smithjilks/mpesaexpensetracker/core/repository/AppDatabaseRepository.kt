package com.smithjilks.mpesaexpensetracker.core.repository

import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.data.AppDao
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.model.User
import javax.inject.Inject

class AppDatabaseRepository @Inject constructor(
    private val appDao: AppDao
) {

    // User
    fun getUser(): User = appDao.getUser()
    suspend fun insertUser(user: User) = appDao.insertUser(user)
    suspend fun deleteUserData() = appDao.deleteUserData()

    // Records
    fun getAllRecords() = appDao.getAllRecords()
    fun getAllIncomeRecords() = appDao.getAllRecordsByRecordType(AppConstants.INCOME)
    fun getAllExpenseRecords() = appDao.getAllRecordsByRecordType(AppConstants.EXPENSE)
    fun getRecentRecords() = appDao.getRecentRecords()

    fun getFilteredRecords(
        recordType: String? = null,
        category: String? = null,
        startDate: Long? = null,
        endDate: Long? = null,
        byHighestAmount: Boolean = false,
        byNewest: Boolean = false
    ) = appDao.getFilteredRecords(recordType, category, startDate, endDate, byHighestAmount, byNewest)

    suspend fun getRecordById(id: Int) = appDao.getRecordById(id)
    suspend fun getRecordByTransactionRef(transactionRef: String) =
        appDao.getRecordByTransactionRef(transactionRef)
    suspend fun insertRecord(record: Record) = appDao.insertRecord(record)

    suspend fun insertRecords(records: List<Record>) = appDao.insertRecords(records)
    suspend fun updateRecord(record: Record) = appDao.updateRecord(record)
    suspend fun deleteRecord(record: Record) = appDao.deleteRecord(record)
    suspend fun deleteAllRecords() = appDao.deleteAllRecords()

}