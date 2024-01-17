package com.example.mvvmsearch

import com.example.mvvmsearch.ui.SearchResult

data class SearchViewState(
    val isBackButtonVisible: Boolean = false,
    val searchInVisibleMapText: String = "",
    val queryText: QueryTextUpdate = QueryTextUpdate.Update(""),
    val searchResult: List<SearchResult> = listOf()
)

sealed class QueryTextUpdate {
    object None : QueryTextUpdate()
    class Update(val text: String) : QueryTextUpdate()
}
