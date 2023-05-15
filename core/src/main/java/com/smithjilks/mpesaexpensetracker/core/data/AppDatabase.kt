package com.smithjilks.mpesaexpensetracker.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smithjilks.mpesaexpensetracker.core.model.Category
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.model.User

@Database(
    entities = [User::class, Category::class, Record::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

}