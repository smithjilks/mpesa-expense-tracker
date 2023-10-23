package com.smithjilks.mpesaexpensetracker.core.model

data class CategorySummary(
    val category: String,
    val categoryType: String,
    val total: Int,
    val categoryImageResourceId: Int,
)
