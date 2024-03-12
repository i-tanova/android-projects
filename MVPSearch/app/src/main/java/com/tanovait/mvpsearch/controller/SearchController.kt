package com.tanovait.mvpsearch.controller

import com.tanovait.mvpsearch.adapter.SearchResult

class SearchController {

    fun search(query: String): List<SearchResult>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            SearchResult("Result 1", "Description 1"),
            SearchResult("Result 2", "Description 2"),
            SearchResult("Result 3", "Description 3")
        )
       return results
    }

    fun categorySearch(): List<SearchResult> {
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        return listOf(
            SearchResult("Category 1", "Description 1"),
            SearchResult("Category 2", "Description 2"),
            SearchResult("Category 3", "Description 3")
        )
    }



    fun searchInVisibleMap(): List<SearchResult>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            SearchResult("Map 1", "Description 1"),
            SearchResult("Map 2", "Description 2"),
            SearchResult("Map 3", "Description 3")
        )
        return results
    }

}