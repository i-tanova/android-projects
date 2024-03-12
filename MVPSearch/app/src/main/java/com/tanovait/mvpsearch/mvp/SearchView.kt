package com.tanovait.mvpsearch.mvp

import com.tanovait.mvpsearch.adapter.SearchResult

interface SearchView {
   fun onViewCreated()

    fun displaySearchResults(searchResults: List<SearchResult>)

    fun displayError(error: String)

    fun onBackButtonVisibilityChanged(isVisible: Boolean)

    fun setSearchViewText(text: String?)
}