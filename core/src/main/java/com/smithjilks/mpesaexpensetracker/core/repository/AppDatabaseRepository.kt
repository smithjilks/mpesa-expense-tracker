package com.smithjilks.mpesaexpensetracker.core.repository

import com.smithjilks.mpesaexpensetracker.core.data.AppDao
import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppDatabaseRepository @Inject constructor(
    private val appDao: AppDao
    ) {

    // User
    fun getUser(): User = appDao.getUser()
    suspend fun insertUser(user: User) = appDao.insertUser(user)
    suspend fun deleteUserData() = appDao.deleteUserData()

    // Categories
    fun getCategories() = appDao.getAllCategories()
    suspend fun getCategoryById(id: Int) = appDao.getCategoryById(id)
    suspend fun insertCategory(category: Category) = appDao.insertCategory(category)
    suspend fun updateCategory(category: Category) = appDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = appDao.deleteCategory(category)
    suspend fun deleteAllCategories() = appDao.deleteAllCategories()

    // Records
    fun getRecords() = appDao.getAllCategories()
    suspend fun getRecordById(id: String) = appDao.getRecordById(id)
    suspend fun insertRecord(record: Record) = appDao.insertRecord(record)
    suspend fun updateRecord(record: Record) = appDao.updateRecord(record)
    suspend fun deleteRecord(record: Record) = appDao.deleteRecord(record)
    suspend fun deleteAllRecords() = appDao.deleteAllRecords()

}