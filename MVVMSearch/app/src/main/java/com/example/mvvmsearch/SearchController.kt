package com.example.mvvmsearch

import com.example.mvvmsearch.ui.SearchResult

class SearchController {

    fun querySearch(query: String): List<SearchResult>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            SearchResult("Query 1", "Description 1"),
            SearchResult("Query 2", "Description 2"),
            SearchResult("Query 3", "Description 3")
        )
       return results
    }

    fun categorySearch(): List<SearchResult>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            SearchResult("Category 1", "Description 1"),
            SearchResult("Category 2", "Description 2"),
            SearchResult("Category 3", "Description 3")
        )
        return results
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