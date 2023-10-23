package com.smithjilks.mpesaexpensetracker.core.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey
    @NonNull
    val id: Int = 0,
    val name: String,
    val imageId: Int?
)
