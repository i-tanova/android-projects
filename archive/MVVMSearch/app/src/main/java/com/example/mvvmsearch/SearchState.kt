package com.example.mvvmsearch

sealed class SearchState

class Init: SearchState()
class Open: SearchState()
class QuerySearch : SearchState()
class CategorySearch : SearchState()
class VisibleMapSearch : SearchState()

