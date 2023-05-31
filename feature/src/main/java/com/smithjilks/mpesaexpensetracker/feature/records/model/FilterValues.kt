package com.smithjilks.mpesaexpensetracker.feature.records.model

data class FilterValues(
    var recordType: String? = null,
    var category: String? = null,
    var startDate: Long? = null,
    var endDate: Long? = null,
    var byHighestAmount: Boolean = false,
    var byNewest: Boolean = false
)
