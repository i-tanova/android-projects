package com.tanovait.mvpsearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.tanovait.mvpsearch.adapter.ResultAdapter
import com.tanovait.mvpsearch.adapter.SearchResult
import com.tanovait.mvpsearch.databinding.FragmentSearchBinding
import com.tanovait.mvpsearch.mvp.SearchView
import com.tanovait.mvpsearch.mvp.SearchViewPresenter
import java.util.logging.Logger

class SearchFragment : Fragment(), SearchView{

    private var searchPresenter: SearchViewPresenter = SearchViewPresenter()

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: ResultAdapter
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("SearchFragment", "onCreateView()")
        adapter = ResultAdapter()
        binding = FragmentSearchBinding.inflate(inflater).apply {
            recyclerView.adapter = adapter
            filterButton.setOnClickListener {
                viewModel.onFilter()
            }
            backButton.setOnClickListener {
               viewModel.onBackButtonPressed()
            }
            searchView.doOnTextChanged { text, start, before, count ->
                viewModel.onTextChanged(text.toString())
            }
            searchView.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    viewModel.onSearchViewClick()
                }
            })
            categorySearch.setOnClickListener {
               viewModel.onCategorySearch()
            }
            searchInVisibleMapAreaButton.setOnClickListener {
               viewModel.onSearchInVisibleMapArea()
            }
        }

        // This is just to showcase if we cleaned the flow (consumed the event)
        lifecycleScope.launchWhenStarted {
            viewModel.searchInVisibleMapAreaFlow.collect{
                binding.searchInVisibleMapArea.text = it
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.searchState.observe(viewLifecycleOwner, Observer { state ->
            onSearchStateChanged(state)
        })
    }

    private fun onSearchStateChanged(state: SearchViewModel.SearchState?) {
        searchPresenter.onSearchStateChanged(state)
    }

    override fun setSearchViewText(text: String?) {
        binding.searchView.setText(text)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onViewCreated() {
        searchPresenter.onViewCreated(this)
    }

    override fun displaySearchResults(searchResults: List<SearchResult>) {
        adapter.setResults(searchResults)
    }

    override fun displayError(error: String) {
        // TODO
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("SearchFragment", "onDestroy")
    }

    override fun onBackButtonVisibilityChanged(isVisible: Boolean) {
        binding.backButton.isVisible = isVisible
    }
}