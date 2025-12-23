package com.example.mvvmsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import kotlinx.coroutines.flow.MutableStateFlow

class SearchViewModel: ViewModel() {

    // move in constructor
    val searchInVisibleMapAreaFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    val searchController: SearchController = SearchController()
    // move in constructor

    val searchResults = MutableLiveData<List<Result>>()

    val backButtonVisibility: LiveData<Boolean>
        get() = _backButtonVisibility
    private val _backButtonVisibility = MutableLiveData(false)

    val updateQueryText: LiveData<String?>
        get() = _updateQueryText.distinctUntilChanged().map { it }
    private val _updateQueryText = MutableLiveData<String?>()

    fun search(query: String) {
        searchResults.postValue(searchController.search(query))
    }

    fun categorySearch() {
        reinitializeSearch()
        searchResults.postValue(searchController.categorySearch())
    }

    fun updateQueryText(queryText: String?) {
        _updateQueryText.value = queryText
    }

    fun onSearchQueryChanged(query: String) {
        updateQueryText(query)
        if (query.isNotEmpty()) {
            clearSearchInVisibleMapArea()
            displayBackButton()
            search(query)
        } else {
            reinitializeSearch()
        }
    }

    fun displayBackButton() {
        _backButtonVisibility.value = true
    }

    fun reinitializeSearch() {
        clearSearchInVisibleMapArea()
        updateQueryText("")
        clearSearchResults()
    }

    private fun clearSearchResults() {
        searchResults.postValue(emptyList())
    }

    fun clearSearchInVisibleMapArea() {
        searchInVisibleMapAreaFlow.value = null
    }

    fun onBackButtonPressed() {
        reinitializeSearch()
        _backButtonVisibility.value = false
    }

    fun visibleMapSearch() {
        reinitializeSearch()
        searchInVisibleMapAreaFlow.value = "Searching...."
        searchResults.postValue(searchController.searchInVisibleMap())
    }

    fun onEvent(event: SearchUIEvents) {

    }
}