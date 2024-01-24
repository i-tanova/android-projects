package com.example.mvvmsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmsearch.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn

class MainActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: ResultAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        adapter = ResultAdapter()
        binding.recyclerView.adapter = adapter


        binding.searchButton.setOnClickListener {
            searchViewModel.onEvent(SearchButtonPressed)
        }

        binding.backButton.setOnClickListener {
            searchViewModel.onEvent(BackButtonPressed)
        }

        binding.searchView.doOnTextChanged { text, start, before, count ->
            searchViewModel.onEvent(SearchQueryChanged(text.toString()))
        }

        binding.categorySearch.setOnClickListener {
            searchViewModel.onEvent(SearchCategory("Category"))
        }

        searchViewModel.searchResults.observe(this, Observer { results ->
            adapter.setResults(results)
        })

        lifecycleScope.launchWhenStarted {
            searchViewModel.searchViewState.collect {
                updateUI(it)
            }
        }

        binding.searchInVisibleMapAreaButton.setOnClickListener {
            searchViewModel.onEvent(SearchInVisibleMapArea)
        }
    }

    private fun updateUI(it: SearchViewState) {
        binding.backButton.visibility = if (it.isBackButtonVisible) View.VISIBLE else View.GONE
        binding.searchInVisibleMapArea.text = it.searchInVisibleMapText
        if(it.queryText is QueryTextUpdate.Update){
            binding.searchView.setText(it.queryText.text)
        }
    }
}