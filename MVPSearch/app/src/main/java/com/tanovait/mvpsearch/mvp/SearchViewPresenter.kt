package com.tanovait.mvpsearch.mvp

import com.tanovait.mvpsearch.SearchViewModel
import java.util.logging.Logger

class SearchViewPresenter {
    protected var view: SearchView? = null
    private val logger = Logger.getLogger(SearchViewPresenter::class.java.name)

    fun onViewCreated(createdView: SearchView){
        view = createdView
    }

    fun onSearchStateChanged(state: SearchViewModel.SearchState?){
        logger.info("onSearchStateChanged: $state")
       when(state){
            is SearchViewModel.SearchState.Category-> {
                view?.setSearchViewText("Category")
                view?.onBackButtonVisibilityChanged(true)
                view?.displaySearchResults(state.results)
            }
            is SearchViewModel.SearchState.Query ->{
                view?.onBackButtonVisibilityChanged(true)
                view?.displaySearchResults(state.results)
            }
            is SearchViewModel.SearchState.Map -> {
                view?.onBackButtonVisibilityChanged(true)
                view?.displaySearchResults(state.results)
            }
            SearchViewModel.SearchState.NONE -> {
                view?.onBackButtonVisibilityChanged(false)
                view?.setSearchViewText("")
                view?.displaySearchResults(emptyList())
            }
           null -> TODO()
       }
    }
}