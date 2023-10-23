package com.smithjilks.mpesaexpensetracker.feature.records

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.repository.AppDatabaseRepository
import com.smithjilks.mpesaexpensetracker.core.utils.CoreUtils
import com.smithjilks.mpesaexpensetracker.feature.records.model.FilterValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val repository: AppDatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    var dbRecord by mutableStateOf(
        Record(0, "", "", 0, 0, "", 0, "", "", "", null)
    )
        private set

    private val _allRecordsList = MutableStateFlow<List<Record>>(emptyList())
    val allRecordsList = _allRecordsList.asStateFlow()


    private val _filterValues = MutableStateFlow(FilterValues())
    private val filterValues: StateFlow<FilterValues> = _filterValues


    val filteredRecordsList = filterValues.flatMapLatest { values ->
        repository.getFilteredRecords(
            values.recordType,
            values.category,
            values.startDate,
            values.endDate,
            values.byHighestAmount,
            values.byNewest
        )
    }

    private val instant = Calendar.getInstance()
    val timeNow = CoreUtils.formatDate(instant.timeInMillis)
    val dateToday = CoreUtils.formatTime(instant.timeInMillis)



    init {
        effect {
            repository.getAllRecords().distinctUntilChanged().collect { listOfRecentRecords ->
                if (listOfRecentRecords.isNotEmpty()) {
                    _allRecordsList.value = listOfRecentRecords
                } else {
                    emptyList<Record>()
                }
            }
        }
    }

    fun getRecordById(id: Int) = effect { dbRecord = repository.getRecordById(id) }

    fun addRecord(record: Record) =  effect { repository.insertRecord(record) }

    fun updateFilterValues(values: FilterValues) = effect { _filterValues.value = values }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(ioDispatcher) { block() }    // 4
    }
}