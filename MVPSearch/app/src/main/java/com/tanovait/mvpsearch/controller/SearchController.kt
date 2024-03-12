package com.example.modernmvpsearch.ui.search.controller

import com.example.modernmvpsearch.ui.search.adapter.Result

class SearchController {

    fun search(query: String): List<Result>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            Result("Result 1", "Description 1"),
            com.example.modernmvpsearch.ui.search.adapter.Result("Result 2", "Description 2"),
            Result("Result 3", "Description 3")
        )
       return results
    }

    fun categorySearch(): List<Result>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            Result("Category 1", "Description 1"),
            Result("Category 2", "Description 2"),
            Result("Category 3", "Description 3")
        )
        return results
    }



    fun searchInVisibleMap(): List<Result>{
        // Perform the search operation here and update searchResults LiveData
        // with the search results
        // You can simulate the search operation by adding dummy data
        val results = listOf(
            Result("Map 1", "Description 1"),
            Result("Map 2", "Description 2"),
            Result("Map 3", "Description 3")
        )
        return results
    }

}