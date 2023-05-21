package com.smithjilks.mpesaexpensetracker.core.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "records_table",
    indices = [Index(value = ["transactionRef"], unique = true)]
)
data class Record(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val transactionRef: String,
    val category: String,
    val amount: String,
    val transactionCost: String,
    val note: String,
    val timestamp: Int,
    val account: String,
    val payee: String,
    val recordType: String,
    val recordImageResourceId: Int?
)
