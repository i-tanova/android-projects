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


//data class QuerySearch(val query: String, val progress: Progress) : SearchUIState
//
//data class CategorySearch(val category: String) : SearchUIState
//class VisibleMapSearch : SearchUIState{
//    override fun clear() {
//    }
//
//}
//
//
//sealed interface Progress
//object isLoading : Progress
//object Done : Progress

