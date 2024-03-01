package com.smithjilks.mpesaexpensetracker.core.utils

import androidx.annotation.StringRes
import com.smithjilks.mpesaexpensetracker.core.model.Category

@StringRes
fun List<Category>.getAsRes(stored: Int): Int? {
    return this.firstOrNull{ it.stored == stored}?.resId
}