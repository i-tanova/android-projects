package com.example.mvvmsearch


data class SearchViewState(
    val isBackButtonVisible: Boolean = false,
    val searchInVisibleMapText: String = "",
    val queryText: QueryTextUpdate = QueryTextUpdate.Update(""),
    val searchResult: List<Result> = listOf()
)

sealed class QueryTextUpdate {
    object None : QueryTextUpdate()
    class Update(val text: String) : QueryTextUpdate()
}