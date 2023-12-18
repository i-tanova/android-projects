package com.example.mvvmsearch.ui

interface ISearchViewModel {
    fun reinitialize()
    fun postSearchResults(searchResult: List<SearchResult>)
}