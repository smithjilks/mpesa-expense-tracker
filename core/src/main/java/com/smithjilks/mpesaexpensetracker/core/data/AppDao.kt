package com.smithjilks.mpesaexpensetracker.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // User queries
    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteUserData()


    // Categories Queries
    @Query("SELECT * FROM categories_table")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM categories_table where id =:id")
    suspend fun getCategoryById(id: Int): Category

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(category: Category)

    @Query("DELETE FROM categories_table")
    suspend fun deleteAllCategories()

    @Delete
    suspend fun deleteCategory(category: Category)



    // Records queries
    @Query("SELECT * FROM records_table")
    fun getAllRecords(): Flow<List<Record>>

    @Query("SELECT * FROM records_table ORDER BY id DESC LIMIT 10")
    fun getRecentRecords(): Flow<List<Record>>

    @Query("SELECT * FROM records_table WHERE recordType =:recordType")
    fun getAllRecordsByRecordType(recordType: String): Flow<List<Record>>

    @Query("SELECT * FROM records_table where id =:id")
    suspend fun getRecordById(id: String): Record

    @Query("SELECT * FROM records_table where transactionRef =:transactionRef")
    suspend fun getRecordByTransactionRef(transactionRef: String): Record

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(records: List<Record>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecord(record: Record)

    @Query("DELETE FROM records_table")
    suspend fun deleteAllRecords()

    @Delete
    suspend fun deleteRecord(record: Record)
}


