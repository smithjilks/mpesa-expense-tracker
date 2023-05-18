package com.smithjilks.mpesaexpensetracker.feature.dashboard

import androidx.lifecycle.ViewModel
import com.smithjilks.mpesaexpensetracker.core.repository.AppDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel@Inject constructor(private val repository: AppDatabaseRepository)
    : ViewModel(){
}