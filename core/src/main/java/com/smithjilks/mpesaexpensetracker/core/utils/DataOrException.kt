package com.smithjilks.mpesaexpensetracker.core.utils

class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var isLoading: Boolean? = null,
    var e: E? = null)