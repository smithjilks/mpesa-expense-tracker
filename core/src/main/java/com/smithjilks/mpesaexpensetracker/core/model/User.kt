package com.smithjilks.mpesaexpensetracker.core.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @NonNull
    val email: String,
    val firstName: String,
    val lastName: String,
)
