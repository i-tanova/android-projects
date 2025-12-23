package com.tanovait.mvpsearch

import androidx.lifecycle.*

import com.tanovait.mvpsearch.controller.SearchController
import kotlinx.coroutines.flow.MutableStateFlow
import com.tanovait.mvpsearch.adapter.SearchResult
import com.tanovait.mvpsearch.mvp.SearchViewPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.logging.Logger

class SearchViewModel : ViewModel() {

    val searchInVisibleMapAreaFlow: MutableStateFlow<String?> = MutableStateFlow(null)
    private val searchController: SearchController = SearchController()
    private val searchStateHolder = SearchStateHolder(this)
    private val logger = Logger.getLogger(SearchViewModel::class.java.name)

    val searchState: LiveData<SearchState>
        get() = searchStateHolder._searchState.distinctUntilChanged().map { it }


    private var isFilterOn = false

   sealed class SearchState {
        data class Category( val results: List<SearchResult>) : SearchState()
       data class Query(val results: List<SearchResult>) : SearchState()
        data class Map(val results: List<SearchResult>) : SearchState()
        data object NONE : SearchState()
    }

    fun onBackButtonPressed() {
        searchStateHolder.onEnterState(SearchState.NONE)
    }

    fun onTextChanged(query: String) {
        logger.info("onTextChanged: $query")
        if(searchStateHolder.getState() is SearchState.Category){
            return
        }
        if (query.isNotEmpty()) {
            searchStateHolder.onEnterState(SearchState.Query(searchController.search(query)))
        } else {
            searchStateHolder.onEnterState(SearchState.NONE)
        }
    }

    fun onCategorySearch() {
        searchStateHolder.onEnterState(SearchState.Category(searchController.categorySearch()))
    }

    fun onSearchInVisibleMapArea() {
        searchStateHolder.onEnterState(SearchState.Map(searchController.searchInVisibleMap()))
    }

    private fun reinitializeSearch() {
        clearFilter()
        clearSearchInVisibleMapArea()
    }

    private fun clearSearchInVisibleMapArea() {
        searchInVisibleMapAreaFlow.value = null
    }

    private fun clearFilter() {
        isFilterOn = false
    }

    // Filter results, performing new search
    fun onFilter() {
        isFilterOn = true
        when (searchStateHolder.getState()) {
            is SearchState.Category -> {
                searchStateHolder.onEnterState(SearchState.Category(
                    searchController.categorySearch().filter { it.title.endsWith("1") })
                )
            }
            is SearchState.Query -> {
                searchStateHolder.onEnterState(SearchState.Query(
                    searchController.search("").filter { it.title.endsWith("1") })
                )
            }
           is SearchState.Map -> {
               searchStateHolder.onEnterState(SearchState.Map(
                   searchController.searchInVisibleMap().filter { it.title.endsWith("1") }))
            }
            SearchState.NONE -> Unit
            null -> TODO()
           }
    }

    fun onSearchViewClick() {
        if (searchStateHolder.getState() is SearchState.Category) {
            reinitializeSearch()
        }
    }

    class SearchStateHolder(val searchViewModel: SearchViewModel) {
        val _searchState = MutableLiveData<SearchState>(SearchState.NONE)
        private val logger = Logger.getLogger(SearchStateHolder::class.java.name)
        fun onEnterState(state: SearchState) {
            if(_searchState.value != state){
                searchViewModel.reinitializeSearch()
            }
            logger.info("onSearchStateChanged: $state")
            _searchState.value = state

            if(state is SearchState.Map){
                searchViewModel.searchInVisibleMapAreaFlow.value = "Searching...."
            }
        }
        fun getState() = _searchState.value
    }
}

fun main() =
    runBlocking<Unit> {
      //  val scope = CoroutineScope(Dispatchers.Main)
        val flow = flow<String> {
            while (isActive){
                emit("active....")
                //delay(1000)
            }
        }
println("----------- here ---------")
        val job = flow.onEach {
            print(it)
        }.launchIn(this)
        delay(5000)
        job.cancel()
        this.cancel()
    }


// We need to clean both state and UI
// We see that the view model has a lot of UI related logic, can we extract it?
// back button visibility, clear text, filter selection
// View model logic depends on search state - category, visible map or other.
// each operation behavior depends on search state

// TEST
// enter text -> Result 1, Result 2, back button visible
// clear text -> empty list, back button hidden
// Back button -> empty list, back button hidden
// press category -> Category 1, Category 2, back button visible
// press filter -> Category 1, back button visible
// click search view -> empty list, back button hidden
// search in visible map -> Map 1, Map 2, back button visible, text preserved