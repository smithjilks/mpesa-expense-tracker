package com.smithjilks.mpesaexpensetracker.feature.records

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.repository.AppDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val repository: AppDatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var dbRecord by mutableStateOf(
        Record(0, "","","","","",0,"","","", null)
    )
        private set

    private val _allRecordsList = MutableStateFlow<List<Record>>(emptyList())
    val allRecordsList = _allRecordsList.asStateFlow()
    init {
        viewModelScope.launch(ioDispatcher) {
            repository.getRecentRecords().distinctUntilChanged().collect { listOfRecentRecords ->
                if (listOfRecentRecords.isNotEmpty()) {
                    _allRecordsList.value = listOfRecentRecords
                } else {
                    emptyList<Record>()
                }
            }
        }
    }

    fun getRecordById(id: Int) {
        viewModelScope.launch(ioDispatcher) {
            dbRecord = repository.getRecordById(id)
        }
    }

    fun addRecord(record: Record) {
        viewModelScope.launch(ioDispatcher) {
            repository.insertRecord(record)
        }
    }


}