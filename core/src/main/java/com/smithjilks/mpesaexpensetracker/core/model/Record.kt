package com.smithjilks.mpesaexpensetracker.core.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records_table")
data class Record(
    @PrimaryKey
    @NonNull
    val recordId: String,
    val category: String,
    val amount: String,
    val transactionCost: String,
    val note: String,
    val timestamp: Int,
    val account: String,
    val payee: String,
    val recordType: String
)
