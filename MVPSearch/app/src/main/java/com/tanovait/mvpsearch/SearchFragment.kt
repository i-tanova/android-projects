package com.example.modernmvpsearch.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.modernmvpsearch.databinding.FragmentSearchBinding
import com.example.modernmvpsearch.ui.search.adapter.ResultAdapter

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            searchView.setOnClickListener {
                viewModel.onSearchViewClick()
            }
            categorySearch.setOnClickListener {
                viewModel.onCategorySearch()
            }
            searchInVisibleMapAreaButton.setOnClickListener {
                viewModel.onSearchInVisibleMapArea()
            }
        }

        // This is just to showcase did we cleaned the flow (consumed the event)
        lifecycleScope.launchWhenStarted {
            viewModel.searchInVisibleMapAreaFlow.collect{
                binding.searchInVisibleMapArea.text = it
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.searchResults.observe(viewLifecycleOwner, Observer { results ->
            adapter.setResults(results)
        })
        viewModel.backButtonVisibility.observe(viewLifecycleOwner, Observer { isVisible ->
          binding.backButton.isVisible = isVisible
        })
        viewModel.updateQueryText.observe(viewLifecycleOwner, Observer { text ->
            binding.searchView.setText(text)
        })
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}