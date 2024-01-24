package com.example.mvvmsearch

sealed class SearchUIEvents
    // Initial value
   object Init: SearchUIEvents()
   object BackButtonPressed : SearchUIEvents()
   class SearchQueryChanged(val query: String): SearchUIEvents()
   object SearchInVisibleMapArea: SearchUIEvents()
   class SearchCategory(val categoryName: String): SearchUIEvents()
   object SearchButtonPressed: SearchUIEvents()