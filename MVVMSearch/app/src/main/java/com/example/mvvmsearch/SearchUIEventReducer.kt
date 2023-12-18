package com.example.mvvmsearch

class EventReducer {
    fun onEvent(event: SearchUIEvents): SearchViewState {
        return when (event) {
            BackButtonPressed -> InitUIState
            Init -> InitUIState
            is SearchQueryChanged -> SearchViewState(true)
            SearchButtonPressed -> SearchViewState(true, "", QueryTextUpdate.None)
            SearchInVisibleMapArea -> SearchViewState(true, "searching..", )
            is SearchCategory -> SearchViewState(true, "", QueryTextUpdate.Update(event.categoryName))
        }
    }

    companion object{
        val InitUIState =  SearchViewState()
    }
}