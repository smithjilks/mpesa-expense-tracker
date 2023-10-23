package com.smithjilks.mpesaexpensetracker.feature.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smithjilks.mpesaexpensetracker.core.constants.AppConstants
import com.smithjilks.mpesaexpensetracker.core.model.CategorySummary
import com.smithjilks.mpesaexpensetracker.core.model.Record
import com.smithjilks.mpesaexpensetracker.core.repository.AppDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: AppDatabaseRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _allRecordsList = MutableStateFlow<List<Record>>(emptyList())
    val allRecordsList = _allRecordsList.asStateFlow()

    private val _categoryType = MutableStateFlow(AppConstants.EXPENSE)
    private val _sortByDescending = MutableStateFlow(false)

    val categoryType = _categoryType.asStateFlow()
    val sortByDescending = _sortByDescending.asStateFlow()

    val summarizedCategoriesList =
        _categoryType.combine(_sortByDescending) { categoryType, sortDescending ->
            repository.getSummarizedCategorise(
                recordType = categoryType,
                sortDescending = sortDescending
            )
        }.flatMapLatest { list: Flow<List<CategorySummary>> ->
            list
        }


    init {
        effect {
            repository.getAllRecords().collect { listOfAllRecords ->
                if (listOfAllRecords.isNotEmpty()) {
                    _allRecordsList.value = listOfAllRecords
                } else {
                    emptyList<Record>()
                }
            }
        }

    }

    private fun effect(block: suspend () -> Unit) {
        viewModelScope.launch(ioDispatcher) { block() }
    }


    fun updateCategoryType(categoryType: String) {
        _categoryType.value = categoryType
    }

    fun updateSortingByDescending(value: Boolean) {
        _sortByDescending.value = value
    }


}