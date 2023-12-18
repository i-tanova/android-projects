package com.example.mvvmsearch

import androidx.lifecycle.*
import com.example.mvvmsearch.ui.ISearchViewModel
import com.example.mvvmsearch.ui.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow

class SearchViewModel : ViewModel(), ISearchViewModel {

    private val eventReducer = EventReducer()

    val searchViewState: MutableStateFlow<SearchViewState> = MutableStateFlow(SearchViewState())
    var searchStateHandler: SearchStateHandler = SearchStateHandler(Init2(this))


    // move in constructor
    private val searchInVisibleMapAreaFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val searchController: SearchController = SearchController()
    // move in constructor

    val searchResults = MutableLiveData<List<SearchResult>>()

    private val updateQueryText = MutableStateFlow<String>("")


    fun onEvent(event: SearchUIEvents) {
        when (event) {
            BackButtonPressed -> reinitialize()
            Init -> reinitialize()
            is SearchQueryChanged -> {
                updateQueryText.value = event.query
                when (searchStateHandler.state) {
                    is QuerySearch -> {
                    //    onStateChanged(QuerySearch(updateQueryText, this, searchController))
                        if (updateQueryText.value.isNotEmpty()) {
                            searchResults.postValue(searchController.querySearch(updateQueryText.value))
                        } else {
                            reinitialize()
                        }
                    }
                    is Init2 -> onStateChanged(QuerySearch(updateQueryText, this, searchController))
                    is VisibleMapSearch -> onStateChanged(QuerySearch(updateQueryText, this, searchController))
                    is CategorySearch -> onStateChanged(QuerySearch(updateQueryText, this, searchController))
                }
            }
            SearchInVisibleMapArea -> {
                onStateChanged(VisibleMapSearch(searchInVisibleMapAreaFlow, this, searchController))
            }
            is SearchButtonPressed ->  searchStateHandler.state.onRepeat()
            is SearchCategory -> onStateChanged(
                CategorySearch(
                    event.categoryName,
                    this,
                    searchController
                )
            )
        }
        searchViewState.value = eventReducer.onEvent(event)
    }


    private fun onStateChanged(newState: SearchState) {
        searchStateHandler.handleNewState(newState)
    }

    override fun reinitialize() {
        searchStateHandler.state.clear()
        updateQueryText.value = ""
        searchResults.postValue(emptyList())
    }

    override fun postSearchResults(searchResult: List<SearchResult>) {
        searchResults.postValue(searchResult)
    }

}





