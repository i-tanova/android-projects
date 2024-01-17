package com.example.mvvmsearch

import com.example.mvvmsearch.ui.ISearchViewModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class SearchState(val viewController: ISearchViewModel){
    abstract fun onEnter()
    abstract fun onRepeat()
    abstract fun clear()
}

class SearchStateHandler (initSearchState: SearchState){
    var state = initSearchState
    private set

    fun handleNewState(searchState: SearchState){
        state.clear()
        state = searchState
        state.onEnter()
    }
}

class Init2(viewController: ISearchViewModel) : SearchState(viewController) {
    override fun onEnter() {
        viewController.reinitialize()
    }

    override fun onRepeat() {
        viewController.reinitialize()
    }

    override fun clear() { // nothing to clean
    }
}

class QuerySearch(
    val updateQueryText: MutableStateFlow<String>, viewController: ISearchViewModel,
    val searchController: SearchController
) : SearchState(viewController) {

    override fun onEnter() {
        viewController.reinitialize()
        doSearch()
    }

    override fun onRepeat() {
        doSearch()
    }

    override fun clear() {
        updateQueryText.value = ""
    }

    private fun doSearch() {
        if (updateQueryText.value.isNotEmpty()) {
            viewController.postSearchResults(searchController.querySearch(updateQueryText.value))
        } else {
            viewController.reinitialize()
        }
    }
}

class CategorySearch(
    val category: String,
    viewController: ISearchViewModel,
    val controller: SearchController
) : SearchState(viewController) {

    override fun onEnter() {
        viewController.reinitialize()
        viewController.postSearchResults(controller.categorySearch())
    }

    override fun onRepeat() {
        viewController.postSearchResults(controller.categorySearch())
    }

    override fun clear() {

    }
}

class VisibleMapSearch(
    val searchInVisibleMapAreaFlow: MutableStateFlow<String?>,
    viewController: ISearchViewModel,
    val searchController: SearchController
) : SearchState(viewController) {
    override fun onEnter() {
        viewController.reinitialize()
        searchInVisibleMapAreaFlow.value = "Searching...."
        viewController.postSearchResults(searchController.searchInVisibleMap())
    }

    override fun onRepeat() {
        searchController.searchInVisibleMap()
    }

    override fun clear() {
        searchInVisibleMapAreaFlow.value = ""
    }
}

