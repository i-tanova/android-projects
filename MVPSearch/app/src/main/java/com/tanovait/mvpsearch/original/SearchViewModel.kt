package com.tanovait.mvpsearch.original

import androidx.lifecycle.*

import com.tanovait.mvpsearch.controller.SearchController
import kotlinx.coroutines.flow.MutableStateFlow
import com.tanovait.mvpsearch.adapter.SearchResult

class SearchViewModel2 : ViewModel() {

    val searchResults = MutableLiveData<List<SearchResult>>()
    val searchInVisibleMapAreaFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val searchController: SearchController = SearchController()
    val backButtonVisibility: LiveData<Boolean>
        get() = _backButtonVisibility
    private val _backButtonVisibility = MutableLiveData(false)

    // Clears text when we need to reinitialize search
    val updateQueryText: LiveData<String?>
        get() = _updateQueryText.distinctUntilChanged().map { it }
    private val _updateQueryText = MutableLiveData<String?>()

    private var currentSearchState = SearchState.NONE
    private var isFilterOn = false

    enum class SearchState {
        CATEGORY,
        QUERY,
        MAP,
        NONE
    }

    fun onBackButtonPressed() {
        reinitializeSearch()
        _backButtonVisibility.value = false
    }

    fun onTextChanged(query: String) {
        updateQueryText(query)
        if (query.isNotEmpty()) {
            if(currentSearchState != SearchState.QUERY){
                clearFilter()
            }
            clearSearchInVisibleMapArea()
            displayBackButton()
            setSearchState(SearchState.QUERY)
            searchResults.postValue(searchController.search(query))
        } else {
            reinitializeSearch()
        }
    }

    private fun setSearchState(state: SearchState) {
        currentSearchState = state
    }

    fun onCategorySearch() {
        if(currentSearchState != SearchState.CATEGORY){
            clearFilter()
        }
        reinitializeSearch()
        setSearchState(SearchState.CATEGORY)
        _updateQueryText.value = "Category"
        searchResults.postValue(searchController.categorySearch())
    }

    fun onSearchInVisibleMapArea() {
        if(currentSearchState != SearchState.MAP){
            clearFilter()
        }
        reinitializeSearch()
        // We need to clean this flow later!!
        setSearchState(SearchState.MAP)
        searchInVisibleMapAreaFlow.value = "Searching...."
        searchResults.postValue(searchController.searchInVisibleMap())
    }


    private fun displayBackButton() {
        _backButtonVisibility.value = true
    }

    // Back button visibility is not part of this reinitialize search
    // the logic is not to reinitialze everything so the name is misleading
    private fun reinitializeSearch() {
        clearFilter()
        setSearchState(SearchState.NONE)
        clearSearchInVisibleMapArea()
        updateQueryText("")
        clearSearchResults()
    }

    private fun clearSearchResults() {
        searchResults.postValue(emptyList())
    }

    private fun clearSearchInVisibleMapArea() {
        searchInVisibleMapAreaFlow.value = null
    }

    private fun updateQueryText(queryText: String?) {
        _updateQueryText.value = queryText
    }

    private fun clearFilter() {
        isFilterOn = false
    }

    // Filter results, performing new search
    fun onFilter() {
        isFilterOn = true
        when (currentSearchState) {
            SearchState.CATEGORY -> searchResults.postValue(
                searchController.categorySearch().filter { it.title.endsWith("1") })
            SearchState.QUERY -> searchResults.postValue(
                searchController.search("").filter { it.title.endsWith("1") })
            SearchState.MAP -> searchResults.postValue(
                searchController.searchInVisibleMap().filter { it.title.endsWith("1") })
            SearchState.NONE -> Unit
        }
    }

    fun onSearchViewClick() {
        if (currentSearchState == SearchState.CATEGORY) {
            reinitializeSearch()
        }
    }
}
// We need to clean both state and UI
// We see that the view model has a lot of UI related logic, can we extract it?
// back button visibility, clear text, filter selection
// View model logic depends on search state - category, visible map or other.
// each operation behavior depends on search state