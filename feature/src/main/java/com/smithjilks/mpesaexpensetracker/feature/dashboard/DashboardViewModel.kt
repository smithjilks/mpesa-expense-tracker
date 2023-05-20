package com.smithjilks.mpesaexpensetracker.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.repository.AppDatabaseRepository
import com.smithjilks.mpesaexpensetracker.core.utils.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel@Inject constructor(private val repository: AppDatabaseRepository)
    : ViewModel() {

    private val _recentRecordsList = MutableStateFlow<List<Record>>(emptyList())
    val recentRecordsList = _recentRecordsList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRecentRecords().distinctUntilChanged().collect { listOfRecentRecords ->
                if (listOfRecentRecords.isNotEmpty()) {

                } else {
                    _recentRecordsList.value = listOfRecentRecords
                }
            }
        }
    }
}