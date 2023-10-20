package com.smithjilks.mpesaexpensetracker.core.model

data class AppButton(
    val title: String = "",
    val isIconButton: Boolean = false,
    val iconResource: Int? = null,
    val callback: () -> Unit = {}
)
